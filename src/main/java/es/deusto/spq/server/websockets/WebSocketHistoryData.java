package es.deusto.spq.server.websockets;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import es.deusto.spq.server.data.Message;

public class WebSocketHistoryData extends WebSocketData {

	private ArrayList<Message> messages;
	
	public WebSocketHistoryData(ArrayList<Message> messages) {
		super("History");
		this.setMessages(messages);
	}
	
	@Override
	public String encode() {
		String history = "History";
		for (Message m : messages) {
			String date = Long.toString(m.getTimestamp());
			String user = m.getUser();
			String message = Base64.getEncoder().encodeToString(m.getText().getBytes());
			history += "\n" + date + ";" + user + ";" + message;
		}
		
		return history;		
	}
	
	public static WebSocketHistoryData decodeData(String data) {
		ArrayList<Message> messages = new ArrayList<>();
		for (String msg : data.split("\n")) {
			String[] parts = msg.split(";");
			long date = Long.parseLong(parts[0]);
			String user = parts[1];
			String text = new String(Base64.getDecoder().decode(parts[2]), StandardCharsets.UTF_8);
			messages.add(new Message(text, user, date));
		}
		return new WebSocketHistoryData(messages);
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages2) {
		this.messages = messages2;
	}
}
