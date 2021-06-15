package com.example.pasarYuk.controller;

//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.Market;
import com.example.pasarYuk.services.BuyerService;
import com.example.pasarYuk.services.ChatService;
import com.example.pasarYuk.services.EmailService;
import com.example.pasarYuk.services.MarketService;

import temp.ChatDTO;
import temp.ChathistoryDTO;
import temp.LoginRequest;
import temp.Message;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")

public class BuyerController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private BuyerService buyerService;
	//private BuyerRepository buyerRepository;
	
	//get buyer
	//@CrossOrigin
	@GetMapping("buyers")
	public List<Buyer> getAllBuyer(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("buyerId", 1);
		
		//return this.buyerRepository.findAll();
		//List<Buyer> buyer = (List<Buyer>) buyerRepository.testQuery("weto@gmail.com");
		//buyer.forEach(System.out::println);
		//return this.buyerRepository.findAll();
		return buyerService.listBuyer();
	}
	@GetMapping("buyers2")
	public List<Buyer> getAllBuyer2(HttpSession session){
		Integer id = (Integer)session.getAttribute("buyerId");
		System.out.println(id);
		//if want to clear the HttpSession
		//session.invalidate();
		
		//return this.buyerRepository.findAll();
		//List<Buyer> buyer = (List<Buyer>) buyerRepository.testQuery("weto@gmail.com");
		//buyer.forEach(System.out::println);
		//return this.buyerRepository.findAll();
		return buyerService.listBuyer();
	}
	
	//get buyer by id
	@GetMapping("/buyers/{buyerId}")
	public ResponseEntity<Buyer> getBuyerById(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException {
		Buyer buyer = buyerService.getBuyerById(buyerId);
		return ResponseEntity.ok().body(buyer);
	}
	
	//get buyer's market name
	@GetMapping("/buyersmarketname/{buyerId}")
	public String getMarketName(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException {
		Buyer buyer = buyerService.getBuyerById(buyerId); 
		System.out.println(buyer.getMarketId());
		Market market = marketService.getMarketById(buyer.getMarketId());
		return market.getMarketName();
	}
	
	//save buyer
	@PostMapping("/buyers")
	public Buyer createBuyer(@RequestBody Buyer buyer) {
		Buyer buyerResp = buyerService.addNewBuyer(buyer);
		return buyerResp;
	}
	//login
	@GetMapping("/buyerLogin")
	public Buyer loginBuyer(@RequestBody LoginRequest loginReq) throws ResourceNotFoundException {
		Buyer buyerResp = buyerService.loginBuyer(loginReq);
		return buyerResp;
	}
	//register
	@PostMapping("/buyerRegister/{otp}")
	public Buyer registerBuyer(@RequestBody Buyer buyer, @PathVariable(value = "otp") String otp) throws ResourceNotFoundException {
		Buyer buyerResp = buyerService.registerBuyer(buyer, otp);
		return buyerResp;
	}
	//send otp
	@PostMapping("/buyerOTP/{email}")
	public String sendOTP(@PathVariable(value = "email") String email) {
		String emailLC = email.toLowerCase();
		String buyerResp = emailService.sendOTP(emailLC, "Buyer");
		return buyerResp;
	}
	//update buyer
	@PutMapping("/buyers/{buyerId}")
	public ResponseEntity<Buyer> updateBuyer(@PathVariable(value = "buyerId") Long buyerId, @Valid @RequestBody Buyer buyerDetails) throws ResourceNotFoundException{
//		Buyer buyer = buyerRepository.findById(buyerId)
//				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + buyerId));
//		
//		buyer.setUsername(buyerDetails.getUsername());
//		buyer.setPassword(buyerDetails.getPassword());
//		buyer.setEmail(buyerDetails.getEmail());
//		buyer.setPhoneNumber(buyerDetails.getPhoneNumber());
//		buyer.setAddress(buyerDetails.getAddress());
		
		Buyer buyer = buyerService.updateBuyer(buyerId, buyerDetails);
		
		return ResponseEntity.ok(buyer);
	}
	
	@PutMapping("/buyers/{buyerId}/market/{marketId}")
	public ResponseEntity<Buyer> updateMarket(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "marketId") Long marketId) throws ResourceNotFoundException{
		
		Buyer buyer = buyerService.updateMarket(buyerId, marketId);
		
		return ResponseEntity.ok(buyer);
	}
	
	//delete buyer
	@DeleteMapping("/buyers/{buyerId}")
	public Map<String, Boolean> deleteBuyer(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException{
//		Buyer buyer = buyerRepository.findById(buyerId)
//				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + buyerId));
//		
//		this.buyerRepository.delete(buyer);
//		
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("Deleted", Boolean.TRUE);
//		
		Map<String, Boolean> response = buyerService.deleteBuyer(buyerId);
		return response;
	}
	
////	-------CHAT BUYER---------- role bisa BUYER/SELLER
//	@GetMapping("/listChat/{id}/{role}")
//	public List<ChatDTO> getChatList(@PathVariable(value = "id") Long id, @PathVariable(value = "role") String role) throws ResourceNotFoundException {
//		List<ChatDTO> chat = chatService.getChatListForBuyerId(id, role.toUpperCase());
//		return chat;
//	}
//	
////	@GetMapping("/buyersChatHistory/{buyerId}/{rcvId}/{type}")
////  role can be BUYER/SELEER/STAFF
//	@GetMapping("listChatHistory/{chatId}/{role}")
//	public List<ChathistoryDTO> getChatHistory(@PathVariable(value = "chatId") Long chatId, @PathVariable(value = "role") String role) throws ResourceNotFoundException {
////		List<ChathistoryDTO> chat = chatService.getBuyerChatHistory(buyerId, rcvId, type);
//		List<ChathistoryDTO> chat = chatService.getChatHistory(chatId, role);
//		return chat;
//	}
//	
////	@PostMapping("/buyersSendMessage/{buyerId}/{rcvId}/{type}")
////	public List<ChathistoryDTO> sendMessageFromBuyer(@Valid @RequestBody Message msg, @PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "rcvId") Long rcvId, @PathVariable(value = "type") String type) throws ResourceNotFoundException {
////		List<ChathistoryDTO> chat = chatService.sendMessageFromBuyer(buyerId, rcvId, type, msg.getText());
////		return chat;
////	}
//	
//	@PostMapping("/sendMessage/{chatId}/{role}")
//	public List<ChathistoryDTO> sendMessage(@Valid @RequestBody Message msg, @PathVariable(value = "chatId") Long chatId, @PathVariable(value = "role") String role) throws ResourceNotFoundException {
//		List<ChathistoryDTO> chat = chatService.sendMessage(chatId, role, msg.getText());
//		return chat;
//	}
//	
//	@PostMapping("/newMessage/{buyerId}/{rcvId}")
//	public List<ChathistoryDTO> newMessage(@Valid @RequestBody Message msg, @PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "rcvId") Long rcvId) throws ResourceNotFoundException {
//		List<ChathistoryDTO> chat = chatService.newMessage(buyerId, rcvId, msg.getText());
//		return chat;
//	}
	
//	-------CHAT BUYER----------
	
}
