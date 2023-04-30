package es.deusto.spq.pojo;

public class SessionData {
	
	//private sesion NombreAponer  
	byte[] token;
	long expirationData;

	public SessionData() {
		
	}
	
	public SessionData(byte[] token, long expirationData) {
		this.token = token;
		this.expirationData = expirationData;
	}
	
	public byte[] getToken() {
		return this.token;
	}
	
	public long getExpirationData() {
		return this.expirationData;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}
	
	public void setExpirationData(long data) {
		this.expirationData = data;
	}
}
