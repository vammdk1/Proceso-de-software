package es.deusto.spq.server.websockets;

public class WebSocketSendData extends WebSocketData {

	private String date;
	private String user;
	private String message;
	
	public WebSocketSendData(String date, String user, String message) {
		super("Send");
		this.setDate(date);
		this.setUser(user);
		this.setMessage(message);
	}
	
	@Override
	public String encode() {
		return "Send\n"
				+ getDate() + "\n" +
				getUser() + "\n" +
				getMessage();
	}

	public static WebSocketSendData decodeData(String data) {
		int dateLineEnd = data.indexOf("\n");
		int userLineEnd = data.indexOf("\n", data.indexOf("\n") + 1);
		
		String date = data.substring(0, dateLineEnd);
		String user = data.substring(dateLineEnd + 1, userLineEnd);
		String message = data.substring(userLineEnd + 1, data.length());
		
		return new WebSocketSendData(date, user, message);
	}

	public String getDate() {
		return date;
	}
	
	public String getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
