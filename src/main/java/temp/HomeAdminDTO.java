package temp;

public class HomeAdminDTO {

	private int totalStaff;
	private int totalSeller;
	private int totalGuestStaff;
	private int totalGuestSeller;
	private int totalOngoingOrder;
	private int totalSuccesOrder;
	private int totalCancelOrder;
	
	public HomeAdminDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HomeAdminDTO(int totalStaff, int totalSeller, int totalGuestStaff, int totalGuestSeller, int totalOngoingOrder, int totalSuccesOrder, int totalCancelOrder) {
		super();
		this.totalStaff = totalStaff;
		this.totalSeller = totalSeller;
		this.totalGuestStaff = totalGuestStaff;
		this.totalGuestSeller = totalGuestSeller;
		this.totalOngoingOrder = totalOngoingOrder;
		this.totalSuccesOrder = totalSuccesOrder;
		this.totalCancelOrder = totalCancelOrder;
	}
	
	public int getTotalOngoingOrder() {
		return totalOngoingOrder;
	}
	public void setTotalOngoingOrder(int totalOngoingOrder) {
		this.totalOngoingOrder = totalOngoingOrder;
	}
	public int getTotalSuccesOrder() {
		return totalSuccesOrder;
	}
	public void setTotalSuccesOrder(int totalSuccesOrder) {
		this.totalSuccesOrder = totalSuccesOrder;
	}
	public int getTotalCancelOrder() {
		return totalCancelOrder;
	}
	public void setTotalCancelOrder(int totalCancelOrder) {
		this.totalCancelOrder = totalCancelOrder;
	}
	public int getTotalStaff() {
		return totalStaff;
	}
	public void setTotalStaff(int totalStaff) {
		this.totalStaff = totalStaff;
	}
	public int getTotalSeller() {
		return totalSeller;
	}
	public void setTotalSeller(int totalSeller) {
		this.totalSeller = totalSeller;
	}
	public int getTotalGuestStaff() {
		return totalGuestStaff;
	}
	public void setTotalGuestStaff(int totalGuestStaff) {
		this.totalGuestStaff = totalGuestStaff;
	}
	public int getTotalGuestSeller() {
		return totalGuestSeller;
	}
	public void setTotalGuestSeller(int totalGuestSeller) {
		this.totalGuestSeller = totalGuestSeller;
	}
	
	
}
