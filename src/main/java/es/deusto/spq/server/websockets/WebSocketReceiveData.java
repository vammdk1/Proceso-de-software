package es.deusto.spq.server.websockets;

public class WebSocketReceiveData extends WebSocketData {

	private long date;
	private String user;
	private String message;
	
	public WebSocketReceiveData(long date, String user, String message) {
		super("Receive");
		this.setDate(date);
		this.setUser(user);
		this.setMessage(message);
	}
	
	@Override
	public String encode() {
		return "Receive\n"
				+ getDate() + "\n" +
				getUser() + "\n" +
				getMessage();
	}

	public static WebSocketReceiveData decodeData(String data) {
		int dateLineEnd = data.indexOf("\n");
		int userLineEnd = data.indexOf("\n", data.indexOf("\n") + 1);
		
		long date = Long.parseLong(data.substring(0, dateLineEnd));
		String user = data.substring(dateLineEnd + 1, userLineEnd);
		String message = data.substring(userLineEnd + 1, data.length());
		
		return new WebSocketReceiveData(date, user, message);
	}

	public long getDate() {
		return date;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getMessage() {
		return message;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
