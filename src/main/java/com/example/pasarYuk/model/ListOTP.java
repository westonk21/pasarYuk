package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "listotp")
public class ListOTP {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long listotpId;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "otp")
	private int otp;
	
	@Column(name = "timestamp")
	private String timestamp;

	public ListOTP() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListOTP(String email, int otp, String timestamp) {
		super();
		this.email = email;
		this.otp = otp;
		this.timestamp = timestamp;
	}

	public long getListotpId() {
		return listotpId;
	}

	public void setListotpId(long listotpId) {
		this.listotpId = listotpId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
