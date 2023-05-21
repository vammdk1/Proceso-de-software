/** \file
 * La clase RegisterData sirve de intermediario entre el cliente y el servidor para la información correspodiente al registro.
 */
package es.deusto.spq.pojo;

public class RegisterData {
	
	private String login;
	private String password;
	
	/**
	 * Constructor vacío al que se le van añadiendo atributos.
	 */
	public RegisterData() {
		
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
