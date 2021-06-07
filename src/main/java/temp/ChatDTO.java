package temp;


public class ChatDTO {
	
	private long chatId;
	private ChatUser sender;
	private ChatUser receiver;
	private String type;
	private String lastTimestamp;
	private String lastMessage;
	public ChatDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ChatDTO(ChatUser sender, ChatUser receiver, String type, String lastTimestamp, String lastMessage) {
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
	public ChatUser getSender() {
		return sender;
	}
	public void setSender(ChatUser sender) {
		this.sender = sender;
	}
	public ChatUser getReceiver() {
		return receiver;
	}
	public void setReceiver(ChatUser receiver) {
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
