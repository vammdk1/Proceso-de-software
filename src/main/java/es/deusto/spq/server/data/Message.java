/** \file
 * La clase Message contiene la información que se enviará en un mensaje. 
 */
package es.deusto.spq.server.data;

import java.util.Date;

/**
 * Clase Mensaje
 * @author Marian
 *
 */
public class Message {
	String user = null;
	private String text = null;
	private long timestamp;

	/**
	 * Constructor de un mensaje a partir del texto y el usuario que lo manda
	 * @param text
	 * @param user
	 */
    public Message(String text, String user) {
        this.setText(text);
        this.setUser(user);
		this.setTimestamp(System.currentTimeMillis());
    }

	public String getUser() {
        return user;
    }
	
	public String getText() {
		return text;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

    public void setUser(String user) {
        this.user = user;
    }

	public void setText(String text) {
		this.text = text;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
    public String toString() {
        return "Message: message --> " + this.getText() + ", timestamp -->  " + new Date(this.getTimestamp());
    }
}