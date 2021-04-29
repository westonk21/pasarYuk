package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "staff")
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long staffId;
	
//	@Column(name = "staff_nik")
//	private String staffNIK;
//	
	@Column(name = "staff_name")
	private String staffName;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "url_staff_photoktp")
	private String urlStaffPhotoktp;
	
	@Column(name = "active")
	private String active;
	
	@Column(name = "market_id")
	private long marketId;
	
	
	public Staff() {
		super();
	}
	
	public Staff(String staffNIK, String phoneNumber, String email, String address, String urlStaffPhotoktp, String active, String staffName) {
		super();
//		this.staffNIK = staffNIK;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		this.urlStaffPhotoktp = urlStaffPhotoktp;
		this.active = active;
		this.staffName = staffName;
	}
	
	public String getUrlStaffPhotoktp() {
		return urlStaffPhotoktp;
	}

	public void setUrlStaffPhotoktp(String urlStaffPhotoktp) {
		this.urlStaffPhotoktp = urlStaffPhotoktp;
	}

	public long getMarketId() {
		return marketId;
	}

	public void setMarketId(long marketId) {
		this.marketId = marketId;
	}

	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public long getStaffId() {
		return staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}
//	public String getStaffNIK() {
//		return staffNIK;
//	}
//	public void setStaffNIK(String staffNIK) {
//		this.staffNIK = staffNIK;
//	}
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
