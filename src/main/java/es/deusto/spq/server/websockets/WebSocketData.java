package es.deusto.spq.server.websockets;

public abstract class WebSocketData {
	
	private String type;
	
	protected WebSocketData(String type) {
		this.type = type;
	}
	
	public static WebSocketData decode(String str) {
		
		int lineEnd;
		String type;
		String data;
		
		if (str.contains("\n")) {
			lineEnd = str.indexOf("\n");
			data = str.substring(lineEnd + 1, str.length());
		} else {
			lineEnd = str.length();
			data = "";
		}
		
		type = str.substring(0, lineEnd);
		
		if (type.equals("Send")) {
			return WebSocketSendData.decodeData(data);
		} else if (type.equals("Receive")) {
			return WebSocketReceiveData.decodeData(data);
 		} else if (type.equals("Join")) {
 			return WebSocketJoinData.decodeData(data);
 		} else if (type.equals("Leave")) {
 			return WebSocketLeaveData.decodeData(data);
 		} else if (type.equals("History")) {
 			return WebSocketHistoryData.decodeData(data);
 		} else {
 			return null;
 		}
	}
	
	public abstract String encode();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
