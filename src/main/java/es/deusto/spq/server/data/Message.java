package es.deusto.spq.server.data;

import java.util.Date;

import es.deusto.spq.server.jdo.User;

public class Message {
	User user = null;
	String text = null;
	long timestamp;

    public Message(String text) {
        this.text = text;
		this.timestamp = System.currentTimeMillis();
    }

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return "Message: message --> " + this.text + ", timestamp -->  " + new Date(this.timestamp);
    }
}