package es.deusto.spq.server.websockets;

public class WebSocketSendData extends WebSocketData {

	private String message;
	
	public WebSocketSendData(String message) {
		super("Send");
		this.setMessage(message);
	}
	
	@Override
	public String encode() {
		return "Send\n"
				+ getMessage();
	}

	public static WebSocketSendData decodeData(String data) {
		int userLineEnd = data.indexOf("\n");
		
		String message = data.substring(userLineEnd + 1, data.length());
		
		return new WebSocketSendData(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
