/** \file
 * La clase Session guarda la información que necesita una sesión para identificar un usuario y lleva un registro de todas las sesiones abiertas.
 */
package es.deusto.spq.server.data;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

import es.deusto.spq.server.jdo.User;
import es.deusto.spq.utils.HexUtils;

public class Session {
	public static final long expirationTime = 600;
	
	static HashMap<String, Session> sessionMap = new HashMap<>(); 
	static SecureRandom sr = new SecureRandom(); 
	
	String token;
	User user;
	long timeStamp;

	/**
	 * Constructor de una sesión a partir del usuario y su token. 
	 * @param user
	 * @param token
	 */
	protected Session(User user, String token) {
		this.user = user;
		this.timeStamp = System.currentTimeMillis()/1000L + expirationTime;
		this.token = token;
	}
	
	/**
	 * Este método determina si una sesión es válida en base al tiempo que lleva activo
	 * @return
	 */
	boolean isValid() {
		return System.currentTimeMillis()/1000L < timeStamp;
	}
	
	/**
	 * Este método refresca la sesión
	 */
	void refreshSession() {
		this.timeStamp = System.currentTimeMillis()/1000L + expirationTime;
	}
	
	/**
	 * Este método invalida una sesión
	 */
	public void invalidateSession() {
		sessionMap.remove(this.token);
	}
	
	public String getToken() {
		return this.token;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public long getTimeStamp() {
		return this.timeStamp;
	}
	
	public static Session getSession(String token) {
		Session session = null;
		ArrayList<String> invalidTokens = new ArrayList<>();
		
		for (Session s : sessionMap.values()) {
			if (!s.isValid())
				invalidTokens.add(s.getToken());
		}
		
		for (String t : invalidTokens) {
			sessionMap.remove(t);
		}
		
		session = sessionMap.get(token);
		if(session != null) {
			session.refreshSession();
		}
		return session;
	}
	 /**
	  * Permite registrar una sesión nueva. 
	  * @param user
	  * @return
	  */
	public static Session createSession(User user) {
		byte[] tokenBytes = new byte[128];
		sr.nextBytes(tokenBytes);
		String token = HexUtils.bytesToHex(tokenBytes);
		
		Session session = new Session(user, token);
		sessionMap.put(token, session);
		
		return session;
	}

}
