package com.example.pasarYuk.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.services.ChatService;

import temp.ChatDTO;
import temp.ChathistoryDTO;
import temp.Message;

@RestController
@RequestMapping("/api/v1")
public class ChatController {
	@Autowired
	private ChatService chatService;
	
	@GetMapping("/listChat/{id}/{role}")
	public List<ChatDTO> getChatList(@PathVariable(value = "id") Long id, @PathVariable(value = "role") String role) throws ResourceNotFoundException {
		List<ChatDTO> chat = chatService.getChatList(id, role.toUpperCase());
		return chat;
	}
	
//	@GetMapping("/buyersChatHistory/{buyerId}/{rcvId}/{type}")
//  role can be BUYER/SELEER/STAFF
//	@GetMapping("listChatHistory/{chatId}/{role}")
//  type just can be BUYER/SELLER/STAFF, KALO BUYER GANTI KE SELLER PAS INQ KE CHAT
	@GetMapping("listChatHistory/{buyerId}/{rcvId}/{type}")
	public List<ChathistoryDTO> getChatHistory(@PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "rcvId") Long rcvId, @PathVariable(value = "type") String type) throws ResourceNotFoundException {
//		List<ChathistoryDTO> chat = chatService.getBuyerChatHistory(buyerId, rcvId, type);
		List<ChathistoryDTO> chat = chatService.getChatHistory(buyerId, rcvId, type);
		return chat;
	}
	
//	@PostMapping("/buyersSendMessage/{buyerId}/{rcvId}/{type}")
//	public List<ChathistoryDTO> sendMessageFromBuyer(@Valid @RequestBody Message msg, @PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "rcvId") Long rcvId, @PathVariable(value = "type") String type) throws ResourceNotFoundException {
//		List<ChathistoryDTO> chat = chatService.sendMessageFromBuyer(buyerId, rcvId, type, msg.getText());
//		return chat;
//	}
	

//	@PostMapping("/sendMessage/{chatId}/{role}")
	//type can be BUYERSL/BUYERST/SELLER/STAFF
	@PostMapping("/sendMessage/{buyerId}/{rcvId}/{type}")
	public String sendMessage(@Valid @RequestBody Message msg, @PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "rcvId") Long rcvId, @PathVariable(value = "type") String type) throws ResourceNotFoundException {
		List<ChathistoryDTO> chat = chatService.sendMessage(buyerId, rcvId, type, msg);
		return "OKE";
	}
	
	@PostMapping("/newMessage/{buyerId}/{rcvId}")
	public List<ChathistoryDTO> newMessage(@Valid @RequestBody Message msg, @PathVariable(value = "buyerId") Long buyerId, @PathVariable(value = "rcvId") Long rcvId) throws ResourceNotFoundException {
		List<ChathistoryDTO> chat = chatService.newMessage(buyerId, rcvId, msg.getText());
		return chat;
	}
}
