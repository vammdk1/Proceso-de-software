package es.deusto.spq.pojo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import es.deusto.spq.utils.ByteUtils;

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
		history += "\nimage;" + Base64.getEncoder().encodeToString(ByteUtils.byteArrayToBits(paint));
		return history;		
	}
	
	public static WebSocketHistoryData decodeData(String data) {
		ArrayList<Message> messages = new ArrayList<>();
		byte[] image = null;
		for (String msg : data.split("\n")) {
			String[] parts = msg.split(";");
			if (msg.startsWith("image;")) {
				String imageString = parts[1];
				image = ByteUtils.bitArrayToBytes(Base64.getDecoder().decode(imageString));
			} else {
				long date = Long.parseLong(parts[0]);
				String user = parts[1];
				String text = new String(Base64.getDecoder().decode(parts[2]), StandardCharsets.UTF_8);
				messages.add(new Message(text, user, date));
			}
			
		}
		return new WebSocketHistoryData(messages, image);
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages2) {
		this.messages = messages2;
	}

	public byte[] getImage() {
		return paint;
	}

	public void setImage(byte[] paint) {
		this.paint = paint;
	}
}
