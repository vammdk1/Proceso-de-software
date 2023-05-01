package es.deusto.spq.server.data;

import java.util.ArrayList;

import es.deusto.spq.server.jdo.User;

public class Room {
	ArrayList<Message> messages = new ArrayList<>();
	ArrayList<User> users = new ArrayList<>();
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

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
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

	public void joinUser(User owner) {
		users.add(owner);
		//TODO: notificar usuarios
	}
	
	public void addMessage(Message message) {
		messages.add(message);
		//TODO: notificar usuarios
	}

	public void deleteUser (User usuario){
		users.remove(users.indexOf(usuario));
	}

	@Override
	public String toString() {
		return "Sala [messages=" + messages + ", users=" + users + ", name=" + name + ", owner=" + owner + "]";
	}
	
	
}
