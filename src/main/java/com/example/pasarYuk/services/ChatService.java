package com.example.pasarYuk.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
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
import temp.ChathistoryDTO;

@Service
public class ChatService {

	@Autowired
	private ChathistoryRepository chathistoryRepository;
	
	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired 
	private ChatRepository chatRepository;
	
	public List<ChatDTO> getChatListForBuyerId(Long id, String role) throws ResourceNotFoundException {
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
				ChatUser chatUserBuyer = new ChatUser();
				chatUserBuyer.set_id(id);
				chatUserBuyer.setName(null);
				chatUserBuyer.setPhotoURL(null);
				
				Seller seller = sellerRepository.findById(chat2.getReceiverId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
				ChatUser chatUserSeller = new ChatUser();
				chatUserSeller.set_id(chat2.getReceiverId());
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
				ChatUser chatUserBuyer = new ChatUser();
				chatUserBuyer.set_id(chat2.getSenderId());
				chatUserBuyer.setName(buyer.getBuyerName());
				chatUserBuyer.setPhotoURL(null);
				
//				Seller seller = sellerRepository.findById(chat2.getReceiverId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
				ChatUser chatUserSeller = new ChatUser();
				chatUserSeller.set_id(id);
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
	
	public List<ChathistoryDTO> getChatHistory(Long chatId, String role ) throws ResourceNotFoundException {
		Chat chat = chatRepository.getByChatId(chatId);
		List<ChathistoryDTO> chatHistory = new ArrayList<ChathistoryDTO>();
		if(chat!=null) {
			List<Chathistory> tempDB = chathistoryRepository.getHistoryBuyerId(chatId);
			int idInc = 1;
			for (Chathistory temp : tempDB) {
				ChathistoryDTO newCHS =  new ChathistoryDTO();
				newCHS.set_id(idInc);
				newCHS.setText(temp.getMessage());
				newCHS.setCreatedAt(temp.getTimestamp());
					ChatUser user = new ChatUser();
					user.set_id(temp.getOwnerId());
					if(role.equals("BUYER")) {
						Buyer buyer = buyerRepository.findById(temp.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));
						user.setName(buyer.getBuyerName());	
						user.setPhotoURL(null);
					}else {
						if(role.equals("SELLER")) {
							Seller seller = sellerRepository.findById(temp.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Seller not found"));
							user.setName(seller.getSellerName());
							user.setPhotoURL(null);
						}else {
							Staff staff = staffRepository.findById(temp.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
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
	
	public List<ChathistoryDTO> sendMessage(Long chatId, String role, String text ) throws ResourceNotFoundException {
		Chat chat = chatRepository.getByChatId(chatId);
//		List<ChathistoryDTO> chatHistory = new ArrayList<ChathistoryDTO>();
		//variabel
		//long chatIdTemp=0;
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String timeStamp = date_format.format(dateTemp);
		
//		if(chat == null) {
//			Chat new1 = new Chat();
//			new1.setSenderId(buyerId);
//			new1.setReceiverId(rcvId);
//			new1.setType(type);
//			new1.setLastMessage(text);
//			new1.setLastTimestamp(timeStamp);
//			
//			chatRepository.save(new1);
//			chatIdTemp = new1.getChatId();
//		}
//		else {
//			chatIdTemp = chat.getChatId();
//		}
		
		Chathistory temp = new Chathistory();
		temp.setChatIdHistory(chatId);
		temp.setMessage(text);
		temp.setTimestamp(timeStamp);
		if(role.equals("BUYER")) {
			temp.setOwnerId(chat.getSenderId());
		}else {
			temp.setOwnerId(chat.getReceiverId());
		}
		chathistoryRepository.save(temp);
		
		chat.setLastMessage(text);
		chat.setLastTimestamp(timeStamp);
		chatRepository.save(chat);
		
		List<ChathistoryDTO> chatHistory = getChatHistory(chatId, role);
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
		
		
		List<ChathistoryDTO> chatHistory = getChatHistory(chatId, "SELLER");
		return chatHistory;
	}
	
}
