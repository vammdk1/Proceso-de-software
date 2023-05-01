package es.deusto.spq.server.logic;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.data.Room;
import es.deusto.spq.server.jdo.User;

public class RoomManager{
   
	
	protected static final Logger logger = LogManager.getLogger();
	
	static HashMap<String, Room> rooms = new HashMap<>();
   
   
   /**
    * 
    * @return devuelve las salas creadas
    */
   public static HashMap<String, Room> getActiveRooms() {
	   return rooms;
   }
   
   /**
    * crea una sala
    * @param roomName
    * @param user
    */
   public static void createRoom(String roomName, User user){
	   rooms.put(roomName, new Room(user, roomName));
   }
	
   //TODO crear las slas con token o con user
   public static void addUserRoom(User user, String roomName ){
		if(rooms.containsKey(roomName)){
			rooms.get(roomName).joinUser(user);
		}else{
			logger.warn("La sala " + roomName + " no existe");
		}
   }

   public static void deleteRoom(String roomName){
        if(rooms.containsKey(roomName)){
        	rooms.remove(roomName);
        }else{
        	logger.warn("La sala " + roomName + " no existe");
        }
   }
   
   public static void deleteUserRoom(User user, String roomName ){
	    if(rooms.containsKey(roomName)){
	    	rooms.get(roomName).deleteUser(user);
	    }else{
	    	logger.warn("La usuario " + user + " no esta en la sala " + roomName);
	    }
   }
   
   public static Room getRoom(String roomName) {
	   return rooms.get(roomName);
   }

}