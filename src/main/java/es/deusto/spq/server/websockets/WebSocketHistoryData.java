package es.deusto.spq.server.websockets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import es.deusto.spq.server.data.Message;
import es.deusto.spq.server.jdo.User;

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
			String message = m.getText();
			history += "\n" + date + "-" + user + "-" + message;
		}
		//System.out.println(messages.get(0) + " " + messages.get(1));
		return history;		
	}
	
	public static WebSocketHistoryData decodeData(String data) {
		int lastNum = 0;
		ArrayList<Message> messages = new ArrayList<>();
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) == '\n') {
				String currentMessage = data.substring(lastNum, i);
				Message message = new Message("", "");
				for (int j = 0; j < currentMessage.length(); j++) {
						int dateLineEnd = currentMessage.indexOf("-");
						int userLineEnd = currentMessage.indexOf("-", currentMessage.indexOf("-") + 1);
						long date = Long.parseLong(currentMessage.substring(0, dateLineEnd));
						String user = currentMessage.substring(dateLineEnd + 1, userLineEnd);
						String text = currentMessage.substring(userLineEnd + 1, currentMessage.length());
						message.setTimestamp(date);
						message.setUser(user);
						message.setText(text);
					if (data.charAt(j) == '\n') {
						break;
					}
				}
				lastNum = i+1;
				messages.add(message);
			} else if (i == data.length() - 1) {
				String currentMessage = data.substring(lastNum, i+1);
				Message message = new Message("", "");
				for (int j = 0; j < currentMessage.length(); j++) {
					int dateLineEnd = currentMessage.indexOf("-");
					int userLineEnd = currentMessage.indexOf("-", currentMessage.indexOf("-") + 1);
					long date = Long.parseLong(currentMessage.substring(0, dateLineEnd));
					String user = currentMessage.substring(dateLineEnd + 1, userLineEnd);
					String text = currentMessage.substring(userLineEnd + 1, currentMessage.length());
					message.setTimestamp(date);
					message.setUser(user);
					message.setText(text);
					if (data.charAt(j) == '\n') {
						break;
					}
				}
				lastNum = i+1;
				messages.add(message);
			}
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
