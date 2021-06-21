package temp;

import com.example.pasarYuk.model.Product;

public class ProductCartDTO {

	private long productId;
	private long sellerId;
	private String productName;
	private String productDesc;
	private long price;
	private long pricePromo;
	private String satuanJual;
	private float avgStar;
	private String category;
	private String urlProductImage;
	private int quantity;
	
	
	public ProductCartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public ProductCartDTO(long productId, long sellerId, String productName, String productDesc, long price,
			long pricePromo, String satuanJual, float avgStar, String category, String urlProductImage, int quantity) {
		super();
		this.productId = productId;
		this.sellerId = sellerId;
		this.productName = productName;
		this.productDesc = productDesc;
		this.price = price;
		this.pricePromo = pricePromo;
		this.satuanJual = satuanJual;
		this.avgStar = avgStar;
		this.category = category;
		this.urlProductImage = urlProductImage;
		this.quantity = quantity;
	}



	public Object clone() {
		ProductCartDTO clone = new ProductCartDTO(this.productId, this.sellerId, this.productName, this.productDesc, this.price, this.pricePromo, this.satuanJual, this.avgStar, this.category, this.urlProductImage, this.quantity);
		return clone;
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



	public String getProductDesc() {
		return productDesc;
	}



	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}



	public long getPrice() {
		return price;
	}



	public void setPrice(long price) {
		this.price = price;
	}



	public long getPricePromo() {
		return pricePromo;
	}



	public void setPricePromo(long pricePromo) {
		this.pricePromo = pricePromo;
	}



	public String getSatuanJual() {
		return satuanJual;
	}



	public void setSatuanJual(String satuanJual) {
		this.satuanJual = satuanJual;
	}



	public float getAvgStar() {
		return avgStar;
	}



	public void setAvgStar(float avgStar) {
		this.avgStar = avgStar;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getUrlProductImage() {
		return urlProductImage;
	}



	public void setUrlProductImage(String urlProductImage) {
		this.urlProductImage = urlProductImage;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
