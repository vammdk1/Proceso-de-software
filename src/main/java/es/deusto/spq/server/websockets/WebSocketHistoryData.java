package es.deusto.spq.server.websockets;

import java.util.ArrayList;

public class WebSocketHistoryData extends WebSocketData {

	private ArrayList<String> messages;
	
	public WebSocketHistoryData(ArrayList<String> messages) {
		super("History");
		this.setMessages(messages);
	}
	
	@Override
	public String encode() {
		String history = "History";
		for (String m : messages) {
			int dateLineEnd = m.indexOf("-");
			int userLineEnd = m.indexOf("-", m.indexOf("-") + 1);
			String date = m.substring(0, dateLineEnd);
			String user = m.substring(dateLineEnd + 1, userLineEnd);
			String message = m.substring(userLineEnd + 1, m.length());
			history += "\n" + date + "-" + user + "-" + message;
		}
		//System.out.println(messages.get(0) + " " + messages.get(1));
		return history;		
	}
	
	public static WebSocketHistoryData decodeData(String data) {
		String m = data;
		int lastNum = 0;
		ArrayList<String> messages = new ArrayList<>();
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '\n') {
				String currentMessage = data.substring(lastNum, i);
				System.out.println(currentMessage);
				lastNum = i+1;
				messages.add(currentMessage);
			} else if (i == data.length() - 1) {
				String currentMessage = data.substring(lastNum, i+1);
				System.out.println(currentMessage);
				messages.add(currentMessage);
			}
		}
		return new WebSocketHistoryData(messages);
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
}
