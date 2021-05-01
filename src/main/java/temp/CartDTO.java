package temp;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

	private CartMarketDTO market;
//	private List<CartProductDTO> product;
//  nama list product nya harus data karena sectionList di react native mesti "data"
	private List<CartProductDTO> data = new ArrayList<CartProductDTO>();
	
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartDTO(CartMarketDTO market, List<CartProductDTO> data) {
		super();
		this.market = market;
		this.data = data;
	}
	
//	public CartMarketDTO getMarket() {
//		return market;
//	}
//
//	public void setMarket(CartMarketDTO market) {
//		this.market = market;
//	}
//
//	public List<CartProductDTO> getProduct() {
//		return product;
//	}
//
//	public void setProduct(List<CartProductDTO> product) {
//		this.product = product;
//	}
	
	public CartMarketDTO getMarket() {
		return market;
	}

	public void setMarket(CartMarketDTO market) {
		this.market = (CartMarketDTO) market.clone();
	}
	
//	public List<CartProductDTO> getData() {
//		return data;
//	}
//
//	public void setData(List<CartProductDTO> data) {
//		this.data = data;
//	}

	public List<CartProductDTO> getData() {
		return data;
	}

	public void setData(List<CartProductDTO> data) {
		for (CartProductDTO temp : data) {
			this.data.add((CartProductDTO) temp.clone());
		}
	}
	
	
	
	
	
	
	
//	public Object clone() {
//		CartDTO clone = new CartDTO();
//		
//	}
	
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
