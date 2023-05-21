/** \file
 * La clase GetRoomData sirve de intermediario entre el cliente y el servidor para la información correspodiente a la sala.
 */
package es.deusto.spq.pojo;

public class GetRoomData {
	String nameRoom;
	boolean isPrivate;
	
	/**
	 * Constructor vacío al que se le van añadiendo atributos.
	 */
	public GetRoomData() {
	}
	
	public String getNameRoom() {
		return nameRoom;
	}
	public void setNameRoom(String nameRoom) {
		this.nameRoom = nameRoom;
	}
	public boolean isPrivate() {
		return isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	
}
