package es.deusto.spq.pojo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WebSocketSendData extends WebSocketData {

	private String message;
	
	/**
	 * Constructor
	 * @param message
	 */
	public WebSocketSendData(String message) {
		super("Send");
		this.setMessage(message);
	}
	
	/**
	 * This method encodes a WebSocketSendData to a string
	 */
	@Override
	public String encode() {
		return "Send\n"
				+ Base64.getEncoder().encodeToString(getMessage().getBytes());
	}

	/**
	 * This method decodes the data from a string to a WebSocketSendData object
	 * @param data
	 * @return
	 */
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
