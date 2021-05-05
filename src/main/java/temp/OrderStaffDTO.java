package temp;

import java.util.ArrayList;
import java.util.List;

import com.example.pasarYuk.model.Buyer;

public class OrderStaffDTO {

	private long orderId;
	private long staffId;
	private String orderDate;
	private String orderTime;
	private String orderStatus;
	private String marketName;
	private String shippingAddress;
	private long shippingFee;
	private long discountShipFee;
	private long subTotal;
	private long total;
	private Buyer buyerDetail;
	private List<LapakSection> listItem = new ArrayList<LapakSection>();
	
	public OrderStaffDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderStaffDTO(long orderId, long staffId, String orderDate, String orderTime, String orderStatus,
			String marketName, String shippingAddress, Buyer buyerDetail, List<LapakSection> listItem, long shippingFee,
			long discountShipFee, long subTotal, long total) {
		super();
		this.orderId = orderId;
		this.staffId = staffId;
		this.orderDate = orderDate;
		this.orderTime = orderTime;
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
	
	public void setListItem(List<LapakSection> listItem) {
		for (LapakSection temp : listItem) {
			this.listItem.add((LapakSection) temp.clone());
		}
	}
	public List<LapakSection> getListItem() {
		return listItem;
		
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
	
	public long getStaffId() {
		return staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
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
	
}
