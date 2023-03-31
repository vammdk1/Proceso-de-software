package es.deusto.spq.server.jdo;

import java.util.ArrayList;

public class Sala {
	ArrayList<Message> messages = new ArrayList<>();
	ArrayList<User> users = new ArrayList<>();
	String name = null;
	User user = null;
	
	public Sala(User user, String name) {
		this.name = name;
		this.user = user;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}
	
	public void joinUser(User user) {
		users.add(user);
		//TODO: notificar usuarios
	}
	
	public void addMessage(Message message) {
		messages.add(message);
		//TODO: notificar usuarios
	}

	@Override
	public String toString() {
		return "Sala [messages=" + messages + ", users=" + users + ", name=" + name + ", user=" + user + "]";
	}
	
	
}