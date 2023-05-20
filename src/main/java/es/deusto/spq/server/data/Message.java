package es.deusto.spq.server.data;

import java.util.Date;

public class Message {
	String user = null;
	private String text = null;
	private long timestamp;

    public Message(String text, String user) {
        this(text, user, System.currentTimeMillis());
    }

	public Message(String text, String user, long timestamp) {
		this.setText(text);
        this.setUser(user);
		this.setTimestamp(timestamp);
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