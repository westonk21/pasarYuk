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
	
	@Column(name = "owner_name")
	private String ownerName;
	
	@Column(name = "owner_photourl")
	private String ownerPhotoURL;
	
	@Column(name = "owner_role")
	private String ownerRole;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "timestamp")
	private String timestamp;

	public Chathistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Chathistory(long chatIdHistory, String image, long ownerId, String message, String timestamp, String ownerName, String ownerPhotoURL, String ownerRole) {
		super();
		this.chatIdHistory = chatIdHistory;
		this.ownerId = ownerId;
		this.image = image;
		this.message = message;
		this.timestamp = timestamp;
		this.ownerName = ownerName;
		this.ownerPhotoURL = ownerPhotoURL;
		this.ownerRole = ownerRole;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerPhotoURL() {
		return ownerPhotoURL;
	}

	public void setOwnerPhotoURL(String ownerPhotoURL) {
		this.ownerPhotoURL = ownerPhotoURL;
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
