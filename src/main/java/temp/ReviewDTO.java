package temp;

public class ReviewDTO {

	private Long productId;
	private String productName;
	private String urlProductImage;
	private float star;
	public ReviewDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReviewDTO(Long productId, String productName, String urlProductImage, float star) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.urlProductImage = urlProductImage;
		this.star = star;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUrlProductImage() {
		return urlProductImage;
	}
	public void setUrlProductImage(String urlProductImage) {
		this.urlProductImage = urlProductImage;
	}
	public float getStar() {
		return star;
	}
	public void setStar(float star) {
		this.star = star;
	}
	
	
}
