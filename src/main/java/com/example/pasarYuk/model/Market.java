package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "market")
public class Market {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long marketId;
	
	@Column(name = "market_name")
	private String marketName;
	
	@Column(name = "market_address")
	private String marketAddress;
	
	@Column(name = "url_market_image")
	private String urlMarketImage;
	
	public Market() {
		super();
	}
	public Market(String marketName, String marketAddress, String urlMarketImage) {
		super();
		this.marketName = marketName;
		this.marketAddress = marketAddress;
		this.urlMarketImage = urlMarketImage;
	}

	
	public String getUrlMarketImage() {
		return urlMarketImage;
	}
	public void setUrlMarketImage(String urlMarketImage) {
		this.urlMarketImage = urlMarketImage;
	}
	
	public long getMarketId() {
		return marketId;
	}
	public void setMarketId(long marketId) {
		this.marketId = marketId;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getMarketAddress() {
		return marketAddress;
	}
	public void setMarketAddress(String marketAddress) {
		this.marketAddress = marketAddress;
	}
	
	
}
