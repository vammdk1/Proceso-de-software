package es.deusto.spq.pojo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WebSocketSendData extends WebSocketData {

	private String message;
	
	public WebSocketSendData(String message) {
		super("Send");
		this.setMessage(message);
	}
	
	@Override
	public String encode() {
		return "Send\n"
				+ Base64.getEncoder().encodeToString(getMessage().getBytes());
	}

	public static WebSocketSendData decodeData(String data) {
		int userLineEnd = data.indexOf("\n");
		
		String message = new String(Base64.getDecoder().decode(data.substring(userLineEnd + 1, data.length())), StandardCharsets.UTF_8);
		
		return new WebSocketSendData(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
