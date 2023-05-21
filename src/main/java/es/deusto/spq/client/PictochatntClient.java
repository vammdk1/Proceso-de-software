package es.deusto.spq.client;

import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.spq.pojo.FriendData;
import es.deusto.spq.pojo.GetRoomData;
import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.pojo.RoomData;
import es.deusto.spq.pojo.SessionData;
import es.deusto.spq.pojo.TokenData;
import es.deusto.spq.pojo.UserData;

public class PictochatntClient {
	protected static final Logger logger = LogManager.getLogger();
	
	private static Client client;
	private static WebTarget webTarget;
	
	public static String token = null;
	private static String username = "NoName";
	
	public static PictochatntClient instace = new PictochatntClient();
	
	public PictochatntClient() {
	}
	/**
	 * Static Init method
	 * @param String host 
	 * @param String port
	 */
	public static void init(String host, String port) {
		logger.info("Inicializando cliante");
		instace = new PictochatntClient();
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest/resource", host, port));
	}
	
	/**
	 * Post method
	 * @param <T>
	 * @param path
	 * @param object
	 * @return Response
	 */
	public <T> Response post(String path, T object) {
		WebTarget postWebTarget = webTarget.path(path);
		Invocation.Builder invoBuilder = postWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invoBuilder.post(Entity.entity(object, MediaType.APPLICATION_JSON));
		logger.warn("Probando " + response.getStatus() + " vs  " + Status.OK.getStatusCode());
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.warn("HTTP Error " + response.getStatus() + " in " + path);
		}
		
		return response;
	}
	/**
	 * Post method
	 * @param path
	 * @return Response
	 */
	public Response post(String path) {
		WebTarget postWebTarget = webTarget.path(path);
		Invocation.Builder invoBuilder = postWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invoBuilder.post(Entity.entity(null, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.warn("HTTP Error " + response.getStatus() + " in " + path);
		}
		
		return response;
	}
	
	public static boolean login(String user, String password) {
		UserData userData = new UserData();
		userData.setLogin(user);
		userData.setPassword(password);
		
		Response response = instace.post("/login", userData);
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return false;
		} else {
			SessionData sessionData = response.readEntity(SessionData.class);
			token = sessionData.getToken();
			username = user;
			return true;
		}
	}
	
	public static boolean register(String user, String password) {
		RegisterData registerData = new RegisterData();
		registerData.setLogin(user);
		registerData.setPassword(password);
		
		Response response = instace.post("/register", registerData);
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return false;
		} else {
			SessionData sessionData = response.readEntity(SessionData.class);
			token = sessionData.getToken();
			username = user;
			return true;
		}
	}
	
	public static boolean logout() {
		if (token == null) {
			logger.warn("Llamando al metodo sin estar logeado");
			return false;
		} else {
			TokenData tokenData = new TokenData();
			tokenData.setToken(token);
			
			Response response = instace.post("/logout", tokenData);
			
			if (response.getStatus() != Status.OK.getStatusCode()) {
				return false;
			} else {
				token = null;
				return true;
			}
		}
	}
	
	public static boolean deleteUser() {
		if (token == null) {
			logger.warn("Llamando al metodo sin estar logeado");
			return false;
		} else {
			TokenData tokenData = new TokenData();
			tokenData.setToken(token);
			
			Response response = instace.post("/deleteUser", tokenData);
			
			if (response.getStatus() != Status.OK.getStatusCode()) {
				return false;
			} else {
				token = null;
				return true;
			}
		}
	}
	
	
	public static ArrayList<String> getFriendList() {
		
		TokenData tokenData = new TokenData();
		tokenData.setToken(token);
		
		Response response = instace.post("/getFriends", tokenData);
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return new ArrayList<String>();
		} else {
			ArrayList<String> resp = response.readEntity(new GenericType<ArrayList<String>>() {});
			return resp;
		}
		
	}
	
	public static boolean addFriend(String name) {
		
		FriendData friendData = new FriendData();
		friendData.setToken(token);
		friendData.setFriendName(name);
		
		Response response = instace.post("/addFriend", friendData);
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public static ArrayList<GetRoomData> getActiveRooms(){
		Response response = instace.post("/getRooms");
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return new ArrayList<GetRoomData>();
		} else {
			ArrayList<GetRoomData> resp = response.readEntity(new GenericType<ArrayList<GetRoomData>>() {});
			return resp;
		}
	}
	
	public static boolean createRoom(String roomName, String password) {
		if (token == null) {
			return false;
		}
		RoomData roomData = new RoomData();
		roomData.setRoomName(roomName);
		if (password.length() == 0) {
			roomData.setPassword(null);
		} else {
			roomData.setPassword(password);
		}
		roomData.setToken(token);
		Response response = instace.post("/createRoom", roomData);
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static String getUsername() {
		
		TokenData tokenData = new TokenData();
		tokenData.setToken(token);
		
		Response response = instace.post("/getUsername", tokenData);
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return "Noname";
		} else {
			String resp = response.readEntity(new GenericType<String>() {});
			return resp;
		}
		
	}
	
	public static boolean deleteRoom(String roomName) {
		if (token == null) {
			return false;
		}
		RoomData roomData = new RoomData();
		roomData.setToken(token);
		roomData.setRoomName(roomName);
		Response response = instace.post("/deleteRoom", roomData);
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return false;
		} else {
			return true;
		}
	}

	public Object getToken() {
		// TODO Auto-generated method stub
		return PictochatntClient.token;
	}
}
