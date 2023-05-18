package es.deusto.spq.server.logic;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;

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
   public static void createRoom(String roomName, User user, String password){
	   rooms.put(roomName, new Room(user, roomName, password));
   }
	
   //TODO crear las slas con token o con user
   public static boolean addUserRoom(User user, String roomName, Session session ){
		if(rooms.containsKey(roomName)){
			return rooms.get(roomName).joinUser(user, session);
		}else{
			logger.warn("La sala " + roomName + " no existe");
			return false;
		}
   }

   public static void deleteRoom(String roomName){
        if(rooms.containsKey(roomName)){
        	rooms.remove(roomName);
        }else{
        	logger.warn("La sala " + roomName + " no existe");
        }
   }
   
   public static boolean deleteUserRoom(User user, String roomName ){
	    if(rooms.containsKey(roomName)){
	    	return rooms.get(roomName).deleteUser(user);
	    }else{
	    	logger.warn("La usuario " + user + " no esta en la sala " + roomName);
	    	return false;
	    }
   }
   
   public static Room getRoom(String roomName) {
	   return rooms.get(roomName);
   }

}