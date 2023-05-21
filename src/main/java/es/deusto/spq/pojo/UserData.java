/** \file
 * La clase UserData sirve de intermediario entre el cliente y el servidor para la información correspodiente al usuario.
 */
package es.deusto.spq.pojo;

public class UserData {

    private String login;
    private String password;
    
    /**
	 * Constructor vacío al que se le van añadiendo atributos.
	 */
    public UserData() {
        // required by serialization
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "[login=" + login + ", password=" + password + "]";
    }
}