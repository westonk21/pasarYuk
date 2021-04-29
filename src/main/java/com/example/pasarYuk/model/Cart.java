package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Cart")
public class Cart {

	@EmbeddedId
	private CartCkey cartId;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "market_id")
	private long marketId;
	
	@Column(name = "check_market")
	private String checkMarket;
	
	@Column(name = "check_item")
	private String checkItem;	

	public Cart() {
		super();
	}

	public Cart(CartCkey cartId, int quantity, long marketId, String checkMarket, String checkItem) {
		super();
		this.cartId = cartId;
		this.quantity = quantity;
		this.marketId = marketId;
		this.checkMarket = checkMarket;
		this.checkItem = checkItem;
	}
	
	public String getCheckMarket() {
		return checkMarket;
	}

	public void setCheckMarket(String checkMarket) {
		this.checkMarket = checkMarket;
	}

	public String getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}

	public long getMarketId() {
		return marketId;
	}

	public void setMarketId(long marketId) {
		this.marketId = marketId;
	}

	public CartCkey getCartId() {
		return cartId;
	}

	public void setCartId(CartCkey cartId) {
		this.cartId = cartId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
