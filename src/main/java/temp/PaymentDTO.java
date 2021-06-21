package temp;

import java.util.ArrayList;
import java.util.List;

import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.Product;

public class PaymentDTO {
	
	private Buyer buyerDetail;
	private String marketName;
	private long shippingFee;
	private long discountShipFee;
	private long subTotal;
	private long total;
	private List<ProductCartDTO> listItem = new ArrayList<ProductCartDTO>();
	
	public PaymentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentDTO(Buyer buyerDetail, List<ProductCartDTO> listItem, String marketName, long shippingFee, long discountShipFee, long subTotal, long total) {
		super();
		this.buyerDetail = buyerDetail;
		this.listItem = listItem;
		this.shippingFee = shippingFee;
		this.discountShipFee = discountShipFee;
		this.subTotal = subTotal;
		this.total = total;
		this.marketName = marketName;
	}
	
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public long getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(long shippingFee) {
		this.shippingFee = shippingFee;
	}
	public long getDiscountShipFee() {
		return discountShipFee;
	}
	public void setDiscountShipFee(long discountShipFee) {
		this.discountShipFee = discountShipFee;
	}
	public long getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(long subTotal) {
		this.subTotal = subTotal;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public Buyer getBuyerDetail() {
		return buyerDetail;
	}
	public void setBuyerDetail(Buyer buyerDetail) {
		this.buyerDetail = buyerDetail;
	}
	public List<ProductCartDTO> getListItem() {
		return listItem;
	}
	public void setListItem(List<ProductCartDTO> listItem) {
		for (ProductCartDTO product : listItem) {
			this.listItem.add((ProductCartDTO) product.clone());
		}
	}
	
	
}
