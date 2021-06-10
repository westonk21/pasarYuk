package temp;


public class ChatDTO {
	
	private long chatId;
	private ChatUser2 sender;
	private ChatUser2 receiver;
	private String type;
	private String lastTimestamp;
	private String lastMessage;
	public ChatDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ChatDTO(ChatUser2 sender, ChatUser2 receiver, String type, String lastTimestamp, String lastMessage) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
		this.lastTimestamp = lastTimestamp;
		this.lastMessage = lastMessage;
	}
	public long getChatId() {
		return chatId;
	}
	public void setChatId(long chatId) {
		this.chatId = chatId;
	}
	public ChatUser2 getSender() {
		return sender;
	}
	public void setSender(ChatUser2 sender) {
		this.sender = sender;
	}
	public ChatUser2 getReceiver() {
		return receiver;
	}
	public void setReceiver(ChatUser2 receiver) {
		this.receiver = receiver;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLastTimestamp() {
		return lastTimestamp;
	}
	public void setLastTimestamp(String lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	
	
}
