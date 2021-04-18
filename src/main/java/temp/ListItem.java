package temp;

//di pake untuk nerima list item dari frontend, pas mau bikin order, sama address nya
public class ListItem {

	
	private Long[] list;
	
	public ListItem() {
		super();
	}

	public ListItem(Long[] list) {
		super();
		this.list = list;
	}

	public Long[] getList() {
		return list;
	}

	public void setList(Long[] list) {
		this.list = list;
	}
}
