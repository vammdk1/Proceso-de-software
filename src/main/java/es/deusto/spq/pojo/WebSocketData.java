package es.deusto.spq.pojo;

public abstract class WebSocketData {
	
	private String type;
	
	protected WebSocketData(String type) {
		this.type = type;
	}
	
	public static WebSocketData decode(String str) {
		int lineEnd = str.indexOf("\n");
		String type = str.substring(0, lineEnd);
		String data = str.substring(lineEnd + 1, str.length());
		
		if (type.equals("Send")) {
			return WebSocketSendData.decodeData(data);
		} else if (type.equals("Receive")) {
			return WebSocketReceiveData.decodeData(data);
 		} else if (type.equals("Leave")) {
 			return WebSocketLeaveData.decodeData(data);
 		} else if (type.equals("History")) {
 			return WebSocketHistoryData.decodeData(data);
 		} else if (type.equals("Paint")) {
 			return WebSocketPaintData.decodeData(data);
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
