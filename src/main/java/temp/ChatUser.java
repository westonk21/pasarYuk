package temp;

public class ChatUser {

	private long _id;
	private String name;
	private String photoURL;
	
	public ChatUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChatUser(Long _id, String name, String photoURL) {
		super();
		this._id = _id;
		this.name = name;
		this.photoURL = photoURL;
	}

	

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
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
