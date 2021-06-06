package temp;

public class ChatUser {

	private long userId;
	private String name;
	private String photoURL;
	
	public ChatUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChatUser(String name, String photoURL) {
		super();
		this.name = name;
		this.photoURL = photoURL;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	
	
}
