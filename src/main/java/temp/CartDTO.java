package temp;

import java.util.List;

import com.example.pasarYuk.model.Market;
import com.example.pasarYuk.model.Product;

public class CartDTO {

	private CartMarketDTO market;
	private List<CartProductDTO> product;
	
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartDTO(CartMarketDTO market, List<CartProductDTO> product) {
		super();
		this.market = market;
		this.product = product;
	}

	public CartMarketDTO getMarket() {
		return market;
	}

	public void setMarket(CartMarketDTO market) {
		this.market = market;
	}

	public List<CartProductDTO> getProduct() {
		return product;
	}

	public void setProduct(List<CartProductDTO> product) {
		this.product = product;
	}
	
//	public CartDTO() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public CartDTO(Product product, Market market, int qty) {
//		super();
//		this.product = product;
//		this.market = market;
//		this.qty = qty;
//	}
//	
//	public Market getMarket() {
//		return market;
//	}
//	public void setMarket(Market market) {
//		this.market = market;
//	}
//	public Product getProduct() {
//		return product;
//	}
//	public void setProduct(Product product) {
//		this.product = product;
//	}
//	public int getQty() {
//		return qty;
//	}
//	public void setQty(int qty) {
//		this.qty = qty;
//	}
//	
	
}
