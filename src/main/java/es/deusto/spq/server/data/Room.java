package es.deusto.spq.server.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.websockets.WebSocketHistoryData;
import es.deusto.spq.server.websockets.WebSocketLeaveData;
import es.deusto.spq.server.websockets.WebSocketReceiveData;

public class Room {
	protected static final Logger logger = LogManager.getLogger();
	
	ArrayList<Message> messages = new ArrayList<>();
	HashMap<User, org.eclipse.jetty.websocket.api.Session> users = new HashMap<User, org.eclipse.jetty.websocket.api.Session>();
	String name = null;
	User owner = null;
	String password = null;
	
	
	public Room(User owner, String name, String password) {
		this.name = name;
		this.owner = owner;
		this.password = password;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public HashMap<User, org.eclipse.jetty.websocket.api.Session> getUsers() {
		return users;
	}

	public String getName() {
		return name;
	}

	public User getOwner() {
		return owner;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean joinUser(User user, org.eclipse.jetty.websocket.api.Session session) {
		if (users.containsKey(user)) {
			return false;
		} else {
			Message message = new Message(user.getLogin() + " ha entrado en la sala.", "SYSTEM");
			this.addMessage(message);		
			users.put(user, session);
			if (session.isOpen()) {
				WebSocketHistoryData historyData = new WebSocketHistoryData(messages);
				try {
					session.getRemote().sendString(historyData.encode());
				} catch (IOException e) {
					logger.error("Error broadcasting messages");
					e.printStackTrace();
				}
			}
			return true;
		}
	}
	
	public boolean deleteUser (User usuario){
		if (!users.containsKey(usuario)) {
			return false;
		}
		WebSocketLeaveData data = new WebSocketLeaveData();
		try {
			if (users.get(usuario).isOpen()) {
				users.get(usuario).getRemote().sendString(data.encode());
			}
		} catch (IOException e) {
			logger.error("Error broadcasting message");
			e.printStackTrace();
		}
		users.remove(usuario);
		Message message = new Message(usuario.getLogin() + " ha salido de la sala.", "SYSTEM");
		this.addMessage(message);
		return true;
	}

	
	public void addMessage(Message message) {
		messages.add(message);
		WebSocketReceiveData data = new WebSocketReceiveData(message.getTimestamp(), message.getUser(), message.getText());
		String encodedData = data.encode();
		for (org.eclipse.jetty.websocket.api.Session s : users.values()) {
			try {
				if (!s.isOpen()) continue;
				s.getRemote().sendString(encodedData);
			} catch (IOException e) {
				logger.error("Error broadcasting message");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String toString() {
		return "Sala [messages=" + messages + ", users=" + users + ", name=" + name + ", owner=" + owner + "]";
	}
	
	
}
