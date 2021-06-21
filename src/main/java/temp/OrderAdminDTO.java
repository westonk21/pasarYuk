package temp;


public class OrderAdminDTO {
	
	private long orderId;
	private String orderTimestamp;
	private String orderStatus;
	private String shippingAddress;
	private String marketName;
	private long total;

	public OrderAdminDTO() {
		super();
	}
	
	public OrderAdminDTO(long total, long buyerId, long staffId, String orderTimestamp, String orderStatus, String shippingAddress, String marketName, long shippingFee, long discountShipFee, String orderType, String orderAcceptTime,String orderCollectTime,String orderShipTime,String orderFinishTime,String orderCancelTime) {
		super();
		this.orderTimestamp = orderTimestamp;
		this.orderStatus = orderStatus;
		this.shippingAddress = shippingAddress;
		this.marketName = marketName;
		this.total = total;
	}

	

	public String getOrderTimestamp() {
		return orderTimestamp;
	}

	public void setOrderTimestamp(String orderTimestamp) {
		this.orderTimestamp = orderTimestamp;
	}

	

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
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

	

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
