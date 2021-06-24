package temp;

public class HomeStaffDTO {

	private String staffName;
	private String phoneNumber;
	private String photoUrl;
	private String active;
	private int ongoingOrder;
	private int finishOrder;
	private int cancelOrder;
	public HomeStaffDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HomeStaffDTO(String staffName, String phoneNumber, String photoUrl, int ongoingOrder, int finishOrder,
			int cancelOrder, String active) {
		super();
		this.staffName = staffName;
		this.phoneNumber = phoneNumber;
		this.photoUrl = photoUrl;
		this.ongoingOrder = ongoingOrder;
		this.finishOrder = finishOrder;
		this.cancelOrder = cancelOrder;
		this.active = active;
	}
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public int getOngoingOrder() {
		return ongoingOrder;
	}
	public void setOngoingOrder(int ongoingOrder) {
		this.ongoingOrder = ongoingOrder;
	}
	public int getFinishOrder() {
		return finishOrder;
	}
	public void setFinishOrder(int finishOrder) {
		this.finishOrder = finishOrder;
	}
	public int getCancelOrder() {
		return cancelOrder;
	}
	public void setCancelOrder(int cancelOrder) {
		this.cancelOrder = cancelOrder;
	}
	
	
}
