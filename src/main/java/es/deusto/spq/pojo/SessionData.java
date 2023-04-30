package es.deusto.spq.pojo;

public class SessionData {
	
	//private sesion NombreAponer  
	String token;
	long expirationData;

	public SessionData() {
		
	}
	
	public SessionData(String token, long expirationData) {
		this.token = token;
		this.expirationData = expirationData;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public long getExpirationData() {
		return this.expirationData;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public void setExpirationData(long data) {
		this.expirationData = data;
	}
}
