package temp;

public class ChathistoryDTO {

	private long _id;
	private String text;
	private String image;
	private String createdAt;
	private ChatUser user;
	
	public ChathistoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ChathistoryDTO(Long _id, String text, String image, String createdAt, ChatUser user) {
		super();
		this._id = _id;
		this.text = text;
		this.image = image;
		this.createdAt = createdAt;
		this.user = user;
	}

	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public ChatUser getUser() {
		return user;
	}
	public void setUser(ChatUser user) {
		this.user = user;
	}
	
	
}
