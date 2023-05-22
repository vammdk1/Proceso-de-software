package es.deusto.spq.pojo;

public class WebSocketLeaveData extends WebSocketData {

	public WebSocketLeaveData() {
		super("Leave");
	}
	
	@Override
	public String encode() {
		return "Leave\n";
	}
	
	public static WebSocketLeaveData decodeData(String data) {
		return new WebSocketLeaveData();
	}
}