package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Orderitem")
public class Orderitem {
	
	@EmbeddedId
	private OrderitemCkey orderitemId;
	
	@Column(name = "quantity")
	private int quantity;
	
	public Orderitem() {
		super();
	}
	public Orderitem(OrderitemCkey orderitemId, int quantity) {
		super();
		this.orderitemId = orderitemId;
		this.quantity = quantity;
	}
	public OrderitemCkey getOrderitemId() {
		return orderitemId;
	}
	public void setOrderitemId(OrderitemCkey orderitemId) {
		this.orderitemId = orderitemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
