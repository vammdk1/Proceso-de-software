/** \file
 * La clase RoomData sirve de intermediario entre el cliente y el servidor para la información correspodiente a la sala.
 */
package es.deusto.spq.pojo;

public class RoomData extends TokenData{
	String roomName;
	String password;

	/**
	 * Constructor vacío al que se le van añadiendo atributos.
	 */
	public RoomData() {
		
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
