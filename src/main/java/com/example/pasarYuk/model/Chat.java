package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chat")
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long chatId;
	
	@Column(name = "sender_id")
	private long senderId;
	
	@Column(name = "receiver_id")
	private long receiverId;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "last_timestamp")
	private String lastTimestamp;
	
	@Column(name = "last_message")
	private String lastMessage;

	public Chat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Chat(long senderId, long receiverId, String type, String lastTimestamp, String lastMessage) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.type = type;
		this.lastTimestamp = lastTimestamp;
		this.lastMessage = lastMessage;
	}

	public long getChatId() {
		return chatId;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLastTimestamp() {
		return lastTimestamp;
	}

	public void setLastTimestamp(String lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	
	
}
