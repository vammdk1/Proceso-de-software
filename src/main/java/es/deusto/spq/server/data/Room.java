/** \file
 * La clase Room contiene la información necesaria para la creación de una sala de chat y la gestión de sus usuarios.
 */
package es.deusto.spq.server.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.websockets.WebSocketLeaveData;
import es.deusto.spq.server.websockets.WebSocketReceiveData;

public class Room {
	protected static final Logger logger = LogManager.getLogger();
	
	ArrayList<Message> messages = new ArrayList<>();
	HashMap<User, org.eclipse.jetty.websocket.api.Session> users = new HashMap<User, org.eclipse.jetty.websocket.api.Session>();
	String name = null;
	User owner = null;
	String password = null;
	
	/**
	 * Constructor de una sala a partir de la persona que la ha creado, el nombre asignado a la sala y su contraseña en caso de ser privada.
	 * @param owner
	 * @param name
	 * @param password
	 */
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

	/**
	 * Añade una nuevo usuario a la sala.
	 * @param user
	 * @param session
	 * @return
	 */
	public boolean joinUser(User user, org.eclipse.jetty.websocket.api.Session session) {
		if (users.containsKey(user)) {
			return false;
		} else {
			Message message = new Message(user.getLogin() + " ha entrado en la sala.", "SYSTEM");
			this.addMessage(message);		
			users.put(user, session);
			return true;
		}
	}
	
	/**
	 * Elimina un usuario de la sala.
	 * @param usuario
	 * @return
	 */
	public boolean deleteUser (User usuario){
		if (!users.containsKey(usuario)) {
			return false;
		}
		WebSocketLeaveData data = new WebSocketLeaveData();
		try {
			users.get(usuario).getRemote().sendString(data.encode());
		} catch (IOException e) {
			logger.error("Error broadcasting message");
			e.printStackTrace();
		}
		users.remove(usuario);
		Message message = new Message(usuario.getLogin() + " ha salido de la sala.", "SYSTEM");
		this.addMessage(message);
		return true;
	}

	/**
	 * Envia un mensaje en la sala.
	 * @param message
	 */
	public void addMessage(Message message) {
		messages.add(message);
		WebSocketReceiveData data = new WebSocketReceiveData(message.getTimestamp(), message.getUser(), message.getText());
		String encodedData = data.encode();
		for (org.eclipse.jetty.websocket.api.Session s : users.values()) {
			try {
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
