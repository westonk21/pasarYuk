package com.example.pasarYuk.twillio;

public class smsRequest {

	private String phoneNumber;
	private String message;
	public smsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public smsRequest(String phoneNumber, String message) {
		super();
		this.phoneNumber = phoneNumber;
		this.message = message;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
