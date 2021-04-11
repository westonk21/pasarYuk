package temp;

import com.example.pasarYuk.model.Product;

public class CartDTO {

	private Product product;
	private int qty;
	
	
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDTO(Product product, int qty) {
		super();
		this.product = product;
		this.qty = qty;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
}
