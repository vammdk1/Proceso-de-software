package es.deusto.spq.pojo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class WebSocketHistoryData extends WebSocketData {

	private ArrayList<Message> messages;
	private byte[] paint;
	
	public WebSocketHistoryData(ArrayList<Message> messages, byte[] paint) {
		super("History");
		this.setMessages(messages);
		this.paint = paint;
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
		//System.out.println(messages.get(0) + " " + messages.get(1));
		String imageString = Base64.getEncoder().encodeToString(paint);
		//history += "\n";
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
