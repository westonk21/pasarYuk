package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "guest")
public class Guest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long guestId;
	
	@Column(name = "guest_name")
	private String guestName;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "comment")
	private String comment;
	
	public Guest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Guest(String guestName, String phoneNumber, String email, String address, String type, String status, String comment) {
		super();
		this.guestName = guestName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		this.type = type;
		this.status = status;
		this.comment = comment;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getGuestId() {
		return guestId;
	}
	public void setGuestId(long guestId) {
		this.guestId = guestId;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
