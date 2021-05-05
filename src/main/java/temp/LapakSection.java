package temp;

import java.util.ArrayList;
import java.util.List;

public class LapakSection {
	private String lapakName;
	private List<CartProductDTO> data = new ArrayList<CartProductDTO>();
	
	public LapakSection() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LapakSection(String lapakName, List<CartProductDTO> data) {
		super();
		this.lapakName = lapakName;
		this.data = data;
	}
	
	public Object clone() {
		LapakSection clone = new LapakSection(this.lapakName, this.data);
		return clone;
	}
	
	public String getLapakName() {
		return lapakName;
	}

	public void setLapakName(String lapakName) {
		this.lapakName = lapakName;
	}

	public List<CartProductDTO> getData() {
		return data;
	}

	public void setData(List<CartProductDTO> data) {
		for (CartProductDTO temp : data) {
			this.data.add((CartProductDTO) temp.clone());
		}
	}
}
