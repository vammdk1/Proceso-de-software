package es.deusto.spq.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.pojo.SessionData;
import es.deusto.spq.pojo.TokenData;
import es.deusto.spq.pojo.UserData;

public class PictochatntClient {
	protected static final Logger logger = LogManager.getLogger();
	
	private static Client client;
	private static WebTarget webTarget;
	
	private static String token = null;
	
	public static void init(String host, String port) {
		logger.info("Inicializando cliante");
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest/resource", host, port));
	}
	
	private static <T> Response post(String path, T object) {
		WebTarget postWebTarget = webTarget.path(path);
		Invocation.Builder invoBuilder = postWebTarget.request(MediaType.APPLICATION_JSON);
		
		Response response = invoBuilder.post(Entity.entity(object, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.warn("HTTP Error " + response.getStatus() + " in " + path);
		}
		
		return response;
	}
	
	public static boolean login(String user, String password) {
		UserData userData = new UserData();
		userData.setLogin(user);
		userData.setPassword(password);
		
		Response response = post("/login", userData);
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return false;
		} else {
			SessionData sessionData = response.readEntity(SessionData.class);
			token = sessionData.getToken();
			return true;
		}
	}
	
	public static boolean register(String user, String password) {
		RegisterData registerData = new RegisterData();
		registerData.setLogin(user);
		registerData.setPassword(password);
		
		Response response = post("/register", registerData);
		
		if (response.getStatus() != Status.OK.getStatusCode()) {
			return false;
		} else {
			SessionData sessionData = response.readEntity(SessionData.class);
			token = sessionData.getToken();
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
			
			Response response = post("/logout", tokenData);
			
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
			
			Response response = post("/deleteUser", tokenData);
			
			if (response.getStatus() != Status.OK.getStatusCode()) {
				return false;
			} else {
				token = null;
				return true;
			}
		}
	}
}
