/** \file
 * La clase Room contiene la información necesaria para la creación de una sala de chat y la gestión de sus usuarios.
 */
package es.deusto.spq.server.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.pojo.Message;
import es.deusto.spq.pojo.WebSocketHistoryData;
import es.deusto.spq.pojo.WebSocketLeaveData;
import es.deusto.spq.pojo.WebSocketPaintData;
import es.deusto.spq.pojo.WebSocketReceiveData;
import es.deusto.spq.pojo.WebSocketPaintData.Mode;
import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.logic.RoomManager;

public class Room {
	protected static final Logger logger = LogManager.getLogger();
	
	final static int size = 512;
	byte[] image = new byte[size*size];
	ArrayList<Message> messages = new ArrayList<>();
	HashMap<User, org.eclipse.jetty.websocket.api.Session> users = new HashMap<User, org.eclipse.jetty.websocket.api.Session>();
	String name = null;
	User owner = null;
	String password = null;
	
	boolean closing = false;
	
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
	

	public byte[] getImage() {
		return image;
	}
	
	public void paint(int x, int y, boolean erase) {
		int startX = x - 1;
		int startY = y - 1;
		int endX = x + 1;
		int endY = y + 1;
		
		if (startX < 0) {
			startX = 0;
		}
		
		if (startY < 0) {
			startY = 0;
		}
		
		if (endX >= size) {
			endX = size - 1;
		}
		
		if (endY >= size) {
			endY = size - 1;
		}
		
		for (int pX = startX; pX <= endX; pX++) {
			for (int pY = startY; pY <= endY; pY++) {
				if (erase) {
					this.setPixel(pX, pY, (byte) 0);
				} else {
					this.setPixel(pX, pY, (byte) 1);
				}
			}
		}
		
		WebSocketPaintData paintData;
		if (!erase) {
			paintData = new WebSocketPaintData(x, y, Mode.Paint);
		} else {
			paintData = new WebSocketPaintData(x, y, Mode.Erase);
		}
		
		String dataString = paintData.encode();
		for (org.eclipse.jetty.websocket.api.Session s : users.values()) {
			try {
				if (!s.isOpen()) continue;
				s.getRemote().sendString(dataString);
			} catch (IOException e) {
				logger.error("Error broadcasting message");
				e.printStackTrace();
			}
		}
	}
	
	private void setPixel(int x, int y, byte value) {
		image[512 * y + x] = value;
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
			if (session.isOpen()) {
				WebSocketHistoryData historyData = new WebSocketHistoryData(messages, image);
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
	
	/**
	 * Elimina un usuario de la sala.
	 * @param usuario
	 * @return
	 */
	public boolean deleteUser (User usuario){
		if (!users.containsKey(usuario)) {
			return false;
		}
		
		if (usuario.equals(owner) && !closing) {
			closing = true;
			RoomManager.deleteRoom(name);
			return true;
		}
		
		if (closing && !usuario.equals(owner)) {
			if (users.get(usuario).isOpen()) {
				users.get(usuario).close();
			}
			return true;
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
		
		if (closing && usuario.equals(owner)) {
			if (users.get(usuario).isOpen()) {
				users.get(usuario).close();
			}
			return true;
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
