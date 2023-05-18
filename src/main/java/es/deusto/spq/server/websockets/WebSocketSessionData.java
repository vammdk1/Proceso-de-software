package es.deusto.spq.server.websockets;

import java.util.List;
import java.util.Map;

import org.eclipse.jetty.websocket.api.Session;

import es.deusto.spq.server.data.Room;
import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.logic.RoomManager;

public class WebSocketSessionData {
	
	private String token;
	private User user;
	private Room room;
	private String password;
	
	public WebSocketSessionData(Session session) {
		Map<String, List<String>> map = session.getUpgradeRequest().getParameterMap();
		if (map.containsKey("token")) {
			token = map.get("token").get(0);
			user = es.deusto.spq.server.data.Session.getSession(getToken()).getUser();
		}
		
		if (map.containsKey("room")) {
			room = RoomManager.getRoom(map.get("room").get(0));
		}
		
		if (map.containsKey("password")) {
			password = map.get("password").get(0);
		}
	}
	
	public boolean equals(WebSocketSessionData session) {
		if (session == null) {
			return false;
		}
		return session.token.equals(this.token);
	}
	
	public int hashCode() {
		return token.hashCode();
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public Room getRoom() {
		return room;
	}

	public String getPassword() {
		return password;
	}
}
