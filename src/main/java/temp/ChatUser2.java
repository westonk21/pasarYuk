package temp;

public class ChatUser2 {

	private long id;
	private String name;
	private String photoURL;
	
	public ChatUser2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChatUser2(Long id, String name, String photoURL) {
		super();
		this.id = id;
		this.name = name;
		this.photoURL = photoURL;
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
