/** \file
 * La clase TokenData sirve de intermediario entre el cliente y el servidor para la información correspodiente al token del usuario.
 */
package es.deusto.spq.pojo;

public class TokenData {
	
	private String token;
	
	/**
	 * Constructor vacío al que se le van añadiendo atributos.
	 */
	public TokenData() {
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
