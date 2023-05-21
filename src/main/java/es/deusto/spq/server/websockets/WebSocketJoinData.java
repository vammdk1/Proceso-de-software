package es.deusto.spq.server.websockets;

public class WebSocketJoinData extends WebSocketData {

	private String token;
	private String roomName;
	
	protected WebSocketJoinData(String token, String roomName) {
		super("Join");
		this.token = token;
		this.roomName = roomName;
	}
	
	@Override
	public String encode() {
		return "Join\n"
				+ getToken() + "\n"
				+ getRoomName();
	}

	public static WebSocketJoinData decodeData(String data) {
		int tokenLineEnd = data.indexOf("\n");
		String token = data.substring(0, tokenLineEnd);
		String roomName = data.substring(tokenLineEnd + 1, data.length());
		
		return new WebSocketJoinData(token, roomName);
	}

	public String getRoomName() {
		return roomName;
	}
	
	public String getToken() {
		return token;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
