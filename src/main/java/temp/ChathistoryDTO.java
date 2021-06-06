package temp;

public class ChathistoryDTO {

	private long id;
	private String text;
	private String createdAt;
	private ChatUser user;
	
	public ChathistoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ChathistoryDTO(String text, String createdAt, ChatUser user) {
		super();
		this.text = text;
		this.createdAt = createdAt;
		this.user = user;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
