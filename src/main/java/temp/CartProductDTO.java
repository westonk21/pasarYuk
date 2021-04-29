package temp;

public class CartProductDTO {
	
	private long productId;
	private String productName;
	private long price;
	private String urlProductImage;
	private int quantity;
	private String checkItem;
	
	public CartProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartProductDTO(long productId, String productName, long price, String urlProductImage, int quantity, String checkItem) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.urlProductImage = urlProductImage;
		this.quantity = quantity;
		this.checkItem = checkItem;
	}

	public Object clone() {
		CartProductDTO clone = new CartProductDTO(this.productId, this.productName, this.price, this.urlProductImage, this.quantity, this.checkItem);
		return clone;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
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

	

	public String getUrlProductImage() {
		return urlProductImage;
	}

	public void setUrlProductImage(String urlProductImage) {
		this.urlProductImage = urlProductImage;
	}

	public String getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	
	
}
