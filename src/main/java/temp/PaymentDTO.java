package temp;

import java.util.ArrayList;
import java.util.List;

import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.Product;

public class PaymentDTO {
	
	private Buyer buyerDetail;
	private List<Product> listItem = new ArrayList<Product>();
	
	public PaymentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentDTO(Buyer buyerDetail, List<Product> listItem) {
		super();
		this.buyerDetail = buyerDetail;
		this.listItem = listItem;
	}
	public Buyer getBuyerDetail() {
		return buyerDetail;
	}
	public void setBuyerDetail(Buyer buyerDetail) {
		this.buyerDetail = buyerDetail;
	}
	public List<Product> getListItem() {
		return listItem;
	}
	public void setListItem(List<Product> listItem) {
		for (Product product : listItem) {
			this.listItem.add((Product) product.clone());
		}
	}
	
	
}
