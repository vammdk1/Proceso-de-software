/** \file
 * La clase FriendData sirve de intermediario entre el cliente y el servidor para la información correspodiente a los amigos.
 */
package es.deusto.spq.pojo;

public class FriendData {
	
	private String token;
	private String friendName;
	
	/**
	 * Constructor vacío al que se le van añadiendo atributos.
	 */
	public FriendData() {
		
	}
	
	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
