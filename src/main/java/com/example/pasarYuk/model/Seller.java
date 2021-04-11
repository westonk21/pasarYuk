package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seller")
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sellerId;
	
	@Column(name = "market_id")
	private long marketId;
	
	@Column(name = "seller_name")
	private String sellerName;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "lapak_name")
	private String lapakName;
	
//	@Column(name = "market_name")
//	private String marketName;
//	
//	@Column(name = "market_address")
//	private String marketAddress;
//	
	public Seller() {
		super();
	}

	public Seller(long marketId, String phoneNumber, String email, String lapakName, String address) {
		super();
		this.marketId = marketId;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.lapakName = lapakName;
		this.address = address;
//		this.marketName = marketName;
//		this.marketAddress = marketAddress;
	}
	
//	public String getMarketName() {
//		return marketName;
//	}
//
//	public void setMarketName(String marketName) {
//		this.marketName = marketName;
//	}
//
//	public String getMarketAddress() {
//		return marketAddress;
//	}
//
//	public void setMarketAddress(String marketAddress) {
//		this.marketAddress = marketAddress;
//	}

	public String getSellerName() {
		return sellerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getLapakName() {
		return lapakName;
	}
	public void setLapakName(String lapakName) {
		this.lapakName = lapakName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public long getMarketId() {
		return marketId;
	}
	public void setMarketId(long marketId) {
		this.marketId = marketId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
}
