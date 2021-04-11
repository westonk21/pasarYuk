package temp;

//di pake untuk nerima list item dari frontend, pas mau bikin order, sama address nya
public class ListItem {

	
	private Long[] list;
	private String address;
	
	public ListItem() {
		super();
	}

	public ListItem(Long[] list, String address) {
		super();
		this.list = list;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long[] getList() {
		return list;
	}

	public void setList(Long[] list) {
		this.list = list;
	}
}
