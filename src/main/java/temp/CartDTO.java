package temp;

import com.example.pasarYuk.model.Market;
import com.example.pasarYuk.model.Product;

public class CartDTO {

	private Product product;
	private Market market;
	private int qty;
	
	
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDTO(Product product, Market market, int qty) {
		super();
		this.product = product;
		this.market = market;
		this.qty = qty;
	}
	
	public Market getMarket() {
		return market;
	}
	public void setMarket(Market market) {
		this.market = market;
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
