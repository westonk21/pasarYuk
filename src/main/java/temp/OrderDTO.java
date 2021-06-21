package temp;

import java.util.List;

import javax.persistence.Column;

import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.Product;
import com.example.pasarYuk.model.Staff;

public class OrderDTO {

	private long orderId;
	private Staff staff;
	private String orderDate;
	private String orderTimestamp;
	private String orderStatus;
	private String marketName;
	private String shippingAddress;
	private long shippingFee;
	private long discountShipFee;
	private long subTotal;
	private long total;
	private Buyer buyerDetail;
	private List<Product> listItem;
	
	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDTO(long orderId, Staff staff, String orderDate, String orderTimestamp, String orderStatus,
			String marketName, String shippingAddress, Buyer buyerDetail, List<Product> listItem, long shippingFee,
			long discountShipFee, long subTotal, long total) {
		super();
		this.orderId = orderId;
		this.staff = staff;
		this.orderDate = orderDate;
		this.orderTimestamp = orderTimestamp;
		this.orderStatus = orderStatus;
		this.marketName = marketName;
		this.shippingAddress = shippingAddress;
		this.buyerDetail = buyerDetail;
		this.listItem = listItem;
		this.shippingFee = shippingFee;
		this.discountShipFee = discountShipFee;
		this.subTotal = subTotal;
		this.total = total;
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
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	//	public long getStaffId() {
//		return staffId;
//	}
//	public void setStaffId(long staffId) {
//		this.staffId = staffId;
//	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderTimestamp() {
		return orderTimestamp;
	}
	public void setOrderTimestamp(String orderTimestamp) {
		this.orderTimestamp = orderTimestamp;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public List<Product> getListItem() {
		return listItem;
	}
	public void setListItem(List<Product> listItem) {
		this.listItem = listItem;
	}
	
	
}
