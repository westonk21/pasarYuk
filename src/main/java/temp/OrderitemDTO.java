package temp;

public class OrderitemDTO {

	private long productId;
	private long sellerId;
	private String productName;
	private long price;
	private int quantity;
	public OrderitemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderitemDTO(long productId, long sellerId, String productName, long price, int quantity) {
		super();
		this.productId = productId;
		this.sellerId = sellerId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
