package temp;

public class HomeAdminDTO {

	private int totalStaff;
	private int totalSeller;
	private int totalGuestStaff;
	private int totalGuestSeller;
	
	public HomeAdminDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HomeAdminDTO(int totalStaff, int totalSeller, int totalGuestStaff, int totalGuestSeller) {
		super();
		this.totalStaff = totalStaff;
		this.totalSeller = totalSeller;
		this.totalGuestStaff = totalGuestStaff;
		this.totalGuestSeller = totalGuestSeller;
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
