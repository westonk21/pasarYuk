package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chathistory")
public class Chathistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "chat_id_history")
	private long chatIdHistory;
	
	@Column(name = "owner_id")
	private long ownerId;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "timestamp")
	private String timestamp;

	public Chathistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Chathistory(long chatIdHistory, long ownerId, String message, String timestamp) {
		super();
		this.chatIdHistory = chatIdHistory;
		this.ownerId = ownerId;
		this.message = message;
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getChatIdHistory() {
		return chatIdHistory;
	}

	public void setChatIdHistory(long chatIdHistory) {
		this.chatIdHistory = chatIdHistory;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
	
}