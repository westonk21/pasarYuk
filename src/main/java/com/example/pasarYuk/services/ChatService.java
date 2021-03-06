package com.example.pasarYuk.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.firebase.PushNotificationRequest;
import com.example.pasarYuk.firebase.PushNotificationService;
import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.Chat;
import com.example.pasarYuk.model.Chathistory;
import com.example.pasarYuk.model.Seller;
import com.example.pasarYuk.model.Staff;
import com.example.pasarYuk.repository.BuyerRepository;
import com.example.pasarYuk.repository.ChatRepository;
import com.example.pasarYuk.repository.ChathistoryRepository;
import com.example.pasarYuk.repository.SellerRepository;
import com.example.pasarYuk.repository.StaffRepository;

import temp.ChatDTO;
import temp.ChatUser;
import temp.ChatUser2;
import temp.ChathistoryDTO;
import temp.Message;

@Service
public class ChatService {

	@Autowired
	private ChathistoryRepository chathistoryRepository;
	
	@Autowired
	private PushNotificationService pushNotificationService;
	
	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired 
	private ChatRepository chatRepository;
	
	public List<ChatDTO> getChatList(Long id, String role) throws ResourceNotFoundException {
		List<Chat> chat = new ArrayList<Chat>();
		List<ChatDTO> listChat = new ArrayList<ChatDTO>();
		if(role.equals("BUYER")) {
			chat = chatRepository.getChatListForBuyerId(id);
			for (Chat chat2 : chat) {
				ChatDTO temp = new ChatDTO();
				temp.setChatId(chat2.getChatId());
				temp.setLastMessage(chat2.getLastMessage());
				temp.setLastTimestamp(chat2.getLastTimestamp());
				temp.setType(chat2.getType());
				
//				Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));
				ChatUser2 chatUserBuyer = new ChatUser2();
				chatUserBuyer.setId(id);
				chatUserBuyer.setName(null);
				chatUserBuyer.setPhotoURL(null);
				
				Seller seller = sellerRepository.findById(chat2.getReceiverId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
				ChatUser2 chatUserSeller = new ChatUser2();
				chatUserSeller.setId(chat2.getReceiverId());
				chatUserSeller.setName(seller.getSellerName());
				chatUserSeller.setPhotoURL(null);
				
				temp.setSender(chatUserBuyer);
				temp.setReceiver(chatUserSeller);
				
				listChat.add(temp);
			}
		}else if(role.equals("SELLER")) {
			chat = chatRepository.getChatListForSellerId(id);
			for (Chat chat2 : chat) {
				ChatDTO temp = new ChatDTO();
				temp.setChatId(chat2.getChatId());
				temp.setLastMessage(chat2.getLastMessage());
				temp.setLastTimestamp(chat2.getLastTimestamp());
				temp.setType(chat2.getType());
				
				Buyer buyer = buyerRepository.findById(chat2.getSenderId()).orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));
				ChatUser2 chatUserBuyer = new ChatUser2();
				chatUserBuyer.setId(chat2.getSenderId());
				chatUserBuyer.setName(buyer.getBuyerName());
				chatUserBuyer.setPhotoURL(null);
				
//				Seller seller = sellerRepository.findById(chat2.getReceiverId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
				ChatUser2 chatUserSeller = new ChatUser2();
				chatUserSeller.setId(id);
				chatUserSeller.setName(null);
				chatUserSeller.setPhotoURL(null);
				
				temp.setSender(chatUserSeller);
				temp.setReceiver(chatUserBuyer);
				
				listChat.add(temp);
			}
		}
		
		return listChat;
	}
	
	public List<ChathistoryDTO> getBuyerChatHistory(Long buyerId, Long rcvId, String type ) throws ResourceNotFoundException {
		Chat chat = chatRepository.findByBuyerIdAndReceiverId(buyerId, rcvId, type);
		List<ChathistoryDTO> chatHistory = new ArrayList<ChathistoryDTO>();
		if(chat!=null) {
			List<Chathistory> tempDB = chathistoryRepository.getHistoryBuyerId(chat.getChatId());
			int idInc = 1;
			for (Chathistory temp : tempDB) {
				ChathistoryDTO newCHS =  new ChathistoryDTO();
				newCHS.set_id(idInc);
				newCHS.setText(temp.getMessage());
				newCHS.setCreatedAt(temp.getTimestamp());
					ChatUser user = new ChatUser();
					user.set_id(temp.getOwnerId());
					if(temp.getOwnerId() == buyerId) {
						Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));
						user.setName(buyer.getBuyerName());	
						user.setPhotoURL(null);
					}else {
						if(type == "SELLER") {
							Seller seller = sellerRepository.findById(rcvId).orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
							user.setName(seller.getSellerName());
							user.setPhotoURL(null);
						}else {
							Staff staff = staffRepository.findById(rcvId).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
							user.setName(staff.getStaffName());
							user.setPhotoURL(null);
						}
					}
				newCHS.setUser(user);
				
				chatHistory.add(newCHS);
				idInc++;
			}
			return chatHistory;
		}else {
			//return empty array
			return chatHistory;
		}
//		return chat;
	}
	
	public List<ChathistoryDTO> getChatHistory(Long buyerId, Long rcvId, String type ) throws ResourceNotFoundException {
		String role=type;
		if(type.equals("BUYER")) {
			role = "SELLER";
		}
		Chat chat = chatRepository.findByBuyerIdAndReceiverId(buyerId, rcvId, role);
		
		List<ChathistoryDTO> chatHistory = new ArrayList<ChathistoryDTO>();
		
		if(chat!=null) {
			long chatId = chat.getChatId();
			List<Chathistory> tempDB = chathistoryRepository.getHistoryBuyerId(chatId);
			int idInc = 1;
			long idRole=0;
			long idMusuh=0;
			int flagSama = 0;
			long id;
			if(tempDB !=null) {
				for (Chathistory find : tempDB) {
					if(find.getOwnerRole().equals(type)) {
						idRole = find.getOwnerId();
					}else {
						idMusuh = find.getOwnerId();
					}
					if(idRole!=0 && idMusuh!=0) {
						if(idRole == idMusuh) {
							flagSama=1;
						}
						break;
					}
				}
				for (Chathistory temp : tempDB) {
//					if(idInc==1) {
//						role = temp.getOwnerRole();
//						id = temp.getChatIdHistory();
//					}else {
//						if(id == temp.getChatIdHistory()) {
//							
//						}
//					}
					
					ChathistoryDTO newCHS =  new ChathistoryDTO();
					newCHS.set_id(idInc);
					newCHS.setText(temp.getMessage());
					newCHS.setImage(temp.getImage());
					newCHS.setCreatedAt(temp.getTimestamp());
						ChatUser user = new ChatUser();
						id=temp.getOwnerId();
//						if(!temp.getOwnerRole().equals(type)) {
//							if(temp.getOwnerId() == idRole) {
//								id++;
//							}
//						}
						if(flagSama == 1) {
							if(!temp.getOwnerRole().equals(type)) {
								id++;
							}
						}
						user.set_id(id);
						user.setName(temp.getOwnerName());
						user.setPhotoURL(temp.getOwnerPhotoURL());
//						if(type.equals("BUYER")) {
//							Buyer buyer = buyerRepository.findById(temp.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));
//							user.setName(buyer.getBuyerName());	
//							user.setPhotoURL(null);
//						}else {
//							if(type.equals("SELLER")) {
//								Seller seller = sellerRepository.findById(temp.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
//								user.setName(seller.getSellerName());
//								user.setPhotoURL(null);
//							}else {
//								Staff staff = staffRepository.findById(temp.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
//								user.setName(staff.getStaffName());
//								user.setPhotoURL(null);
//							}
//						}
					newCHS.setUser(user);
					
					chatHistory.add(newCHS);
					idInc++;
				}
			}
			return chatHistory;
		}else {
			//return empty array
			return chatHistory;
		}
//		return chat;
	}
	
//	public List<ChathistoryDTO> sendMessageFromBuyer(Long buyerId, Long rcvId, String type, String text ) throws ResourceNotFoundException {
//		Chat chat = chatRepository.findByBuyerIdAndReceiverId(buyerId, rcvId, type);
////		List<ChathistoryDTO> chatHistory = new ArrayList<ChathistoryDTO>();
//		//variabel
//		long chatId=0;
//		Date dateTemp = new Date();
//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
//		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
//		String timeStamp = date_format.format(dateTemp);
//		
//		if(chat == null) {
//			Chat new1 = new Chat();
//			new1.setSenderId(buyerId);
//			new1.setReceiverId(rcvId);
//			new1.setType(type);
//			new1.setLastMessage(text);
//			new1.setLastTimestamp(timeStamp);
//			
//			chatRepository.save(new1);
//			chatId = new1.getChatId();
//		}else {
//			chatId = chat.getChatId();
//		}
//		
//		Chathistory temp = new Chathistory();
//		temp.setChatIdHistory(chatId);
//		temp.setMessage(text);
//		temp.setTimestamp(timeStamp);
//		temp.setOwnerId(buyerId);
//		chathistoryRepository.save(temp);
//		
//		List<ChathistoryDTO> chatHistory = getChatHistory(buyerId, rcvId, type);
//		return chatHistory;
//	}
	
	public List<ChathistoryDTO> sendMessage(Long buyerId, Long rcvId, String type, Message msg ) throws ResourceNotFoundException {
		
		String text = msg.getText();
		String image = msg.getImage();
				
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String timeStamp = date_format.format(dateTemp);
		String role;
		if(type.equals("BUYERSL")) {
			role = "SELLER";
		}else if(type.equals("BUYERST")) {
			role = "STAFF";
		}else {
			if(type.equals("SELLER") || type.equals("STAFF")) {
				role = type;
			}else {
				throw new ResourceNotFoundException("Wrong Type input ");
			}
		}
		Chat chat = chatRepository.findByBuyerIdAndReceiverId(buyerId, rcvId, role);
//		List<ChathistoryDTO> chatHistory = new ArrayList<ChathistoryDTO>();
		
		long chatIdTemp;
		
		if(chat == null) {
			Chat new1 = new Chat();
			new1.setSenderId(buyerId);
			new1.setReceiverId(rcvId);
			new1.setType(role);
			new1.setLastMessage(text);
			new1.setLastTimestamp(timeStamp);
			
			chatRepository.save(new1);
			chatIdTemp = new1.getChatId();
			chat = new1;
		}
		else {
			chatIdTemp = chat.getChatId();
			chat.setLastMessage(text);
			chat.setLastTimestamp(timeStamp);
			chatRepository.save(chat);
		}
		
		
		Chathistory temp = new Chathistory();
		temp.setChatIdHistory(chatIdTemp);
		temp.setMessage(text);
		temp.setImage(image);
		temp.setTimestamp(timeStamp);
		if(type.equals("BUYERSL") || type.equals("BUYERST")) {
			temp.setOwnerId(chat.getSenderId());
			Buyer buyer = buyerRepository.findById(temp.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));
			temp.setOwnerName(buyer.getBuyerName());
			temp.setOwnerPhotoURL(buyer.getPhotoUrl());
			temp.setOwnerRole("BUYER");
//			sendToken = buyer.getToken();
		}else {
			temp.setOwnerId(chat.getReceiverId());
			if(type.equals("SELLER")) {
				Seller seller = sellerRepository.findById(temp.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
				temp.setOwnerName(seller.getSellerName());
				temp.setOwnerPhotoURL(seller.getPhotoUrl());
				temp.setOwnerRole(type);
//				sendToken = seller.getToken();
			}else {
				Staff staff = staffRepository.findById(temp.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
				temp.setOwnerName(staff.getStaffName());
				temp.setOwnerPhotoURL(staff.getPhotoUrl());
				temp.setOwnerRole(type);
//				sendToken = staff.getToken();
			}
		}
		chathistoryRepository.save(temp);
		
		String sendToken;
		if(type.equals("BUYERSL")) {
			Seller seller = sellerRepository.findById(chat.getReceiverId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
			sendToken = seller.getToken();
		}else if (type.equals("BUYERST")) {
			Staff staff = staffRepository.findById(chat.getReceiverId()).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
			sendToken = staff.getToken();
		}else {
			Buyer buyer = buyerRepository.findById(chat.getSenderId()).orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));
			sendToken = buyer.getToken();
		}
		
		
		
		if(sendToken!=null) {
			PushNotificationRequest request = new PushNotificationRequest();
			request.setTitle("Ada Pesan Untukmu");
			request.setMessage(text);
			request.setToken(sendToken);
			request.setTopic("");
			pushNotificationService.sendPushNotificationToToken(request);
		}
		
//		List<ChathistoryDTO> chatHistory = getChatHistory(buyerId, role);
		List<ChathistoryDTO> chatHistory = new ArrayList<ChathistoryDTO>();
		return chatHistory;
	}
	
	public List<ChathistoryDTO> newMessage(Long buyerId, Long rcvId, String text ) throws ResourceNotFoundException {
		Chat tempChat = chatRepository.findByBuyerIdAndReceiverId(buyerId, rcvId, "SELLER");
		long chatId;
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String timeStamp = date_format.format(dateTemp);
		
		if(tempChat == null) {
			Chat chat = new Chat();
			chat.setLastMessage(text);
			chat.setLastTimestamp(timeStamp);
			chat.setSenderId(buyerId);
			chat.setReceiverId(rcvId);
			chat.setType("SELLER");
			chatRepository.save(chat);
			chatId = chat.getChatId();
		}else {
			chatId= tempChat.getChatId();
		}
		
		Chathistory temp = new Chathistory();
		temp.setChatIdHistory(chatId);
		temp.setMessage(text);
		temp.setTimestamp(timeStamp);
		temp.setOwnerId(buyerId);
		chathistoryRepository.save(temp);
		
		
//		List<ChathistoryDTO> chatHistory = getChatHistory(chatId, "SELLER");
		List<ChathistoryDTO> chatHistory = new ArrayList<ChathistoryDTO>();
		return chatHistory;
	}
	
}
