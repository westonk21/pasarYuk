package com.example.pasarYuk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	
	@Column(name = "buyer_id")
	private long buyerId;
	
	@Column(name = "staff_id")
	private long staffId;
	
	@Column(name = "order_date")
	private String orderDate;
	
	@Column(name = "order_time")
	private String orderTime;
	
	@Column(name = "order_status")
	private String orderStatus;
	
	@Column(name = "shipping_address")
	private String shippingAddress;
	
	@Column(name = "shipping_fee")
	private long shippingFee;
	
	@Column(name = "discount_ShipFee")
	private long discountShipFee;
	
	@Column(name = "market_name")
	private String marketName;

	public Order() {
		super();
	}
	
	public Order(long buyerId, long staffId, String orderDate, String orderTime, String orderStatus, String shippingAddress, String marketName, long shippingFee, long discountShipFee) {
		super();
		this.buyerId = buyerId;
		this.staffId = staffId;
		this.orderDate = orderDate;
		this.orderTime = orderTime;
		this.orderStatus = orderStatus;
		this.shippingAddress = shippingAddress;
		this.marketName = marketName;
		this.shippingFee = shippingFee;
		this.discountShipFee = discountShipFee;
	}

	public long getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(long shippingFee) {
		this.shippingFee = shippingFee;
	}

	public long getDiscountShipFee() {
		return discountShipFee;
	}

	public void setDiscountShipFee(long discountShipFee) {
		this.discountShipFee = discountShipFee;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
