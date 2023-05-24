package es.deusto.spq.pojo;

public class WebSocketLeaveData extends WebSocketData {

	public WebSocketLeaveData() {
		super("Leave");
	}
	
	/**
	 * This method encodes a WebSocketLeaveData to a string
	 */
	@Override
	public String encode() {
		return "Leave\n";
	}
	
	/**
	 * This method decodes the data from a string to a WebSocketLeaveData object
	 * @param data
	 * @return
	 */
	public static WebSocketLeaveData decodeData(String data) {
		return new WebSocketLeaveData();
	}
}
