package es.deusto.spq.server.data;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

import es.deusto.spq.server.jdo.User;

public class Session {
	public static final long expirationTime = 600;
	
	static HashMap<byte[], Session> sessionMap = new HashMap<>(); 
	static SecureRandom sr = new SecureRandom(); 
	
	byte[] token;
	User user;
	long timeStamp;

	protected Session(User user, byte[] token) {
		this.user = user;
		this.timeStamp = System.currentTimeMillis()/1000L + expirationTime;
		this.token = token;
	}
	
	boolean isValid() {
		return System.currentTimeMillis()/1000L < timeStamp;
	}
	
	void refreshSession() {
		this.timeStamp = System.currentTimeMillis()/1000L + expirationTime;
	}
	
	void invalidateSession() {
		sessionMap.remove(this.token);
	}
	
	public byte[] getToken() {
		return this.token;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public long getTimeStamp() {
		return this.timeStamp;
	}
	
	static Session getSession(byte[] token) {
		Session session = null;
		ArrayList<byte[]> invalidTokens = new ArrayList<>();
		
		for (Session s : sessionMap.values()) {
			if (!s.isValid())
				invalidTokens.add(s.getToken());
		}
		
		for (byte[] t : invalidTokens) {
			sessionMap.remove(t);
		}
		
		session = sessionMap.get(token);
		session.refreshSession();
		return session;
	}
	
	static Session createSession(User user) {
		byte[] token = new byte[128];
		sr.nextBytes(token);
		
		Session session = new Session(user, token);
		sessionMap.put(token, session);
		
		return session;
	}

}
