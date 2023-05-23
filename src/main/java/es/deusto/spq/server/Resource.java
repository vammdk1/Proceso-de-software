package es.deusto.spq.server;

import java.util.ArrayList; 
import java.util.Map;
import java.util.regex.Pattern;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.pojo.FriendData;
import es.deusto.spq.pojo.GetRoomData;
import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.pojo.RoomData;
import es.deusto.spq.pojo.SessionData;
import es.deusto.spq.pojo.TokenData;
import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.data.Room;
import es.deusto.spq.server.data.Session;
import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.logic.RoomManager;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {
	

	protected static final Logger logger = LogManager.getLogger();

	/**
	 * Method that returns all active rooms for an user to connect
	 * @return Returns Ok status with the information of the rooms if everything goes fine or returns 403 status if there is any problem within the process
	 */
	 @POST
	 @Path("/getRooms")
	 public Response getRooms() {
		 ArrayList<GetRoomData> roomDatas = new ArrayList<>();
		 for (Map.Entry<String, Room> entry : RoomManager.getActiveRooms().entrySet()) {
			 GetRoomData roomData = new GetRoomData();
			 roomData.setNameRoom(entry.getKey());
			 roomData.setPrivate(entry.getValue().getPassword() != null);
			 roomDatas.add(roomData);
		 }
		 GenericEntity<ArrayList<GetRoomData>> entity = new GenericEntity<ArrayList<GetRoomData>>(roomDatas) {};
		 return Response.ok().entity(entity).build();
	 }
	 
	 //login and register
	 /**
	  * Method that allows an user to log in the app 
	  * @param userData UserData with the information of the user to create his session 
	  * @return Returns Ok status including the session data if everything goes fine or returns 403 status if there is any problem within the process
	  */
	 @POST
	 @Path("/login")
	 public Response login(UserData userData) {
		 User user = User.find(userData.getLogin());
		 if (user == null || !user.isPasswordCorrect(userData.getPassword())) {
			 logger.info("Usuario o contraseña erroneos");
			 return Response.status(403).entity("Incorrect username or password").build();
		 } else {
			 logger.info("Iniciand sesión");
			 Session session = Session.createSession(user);
			 SessionData sessionData = new SessionData(session.getToken(), session.getTimeStamp());
			 return Response.ok().entity(sessionData).build();
		 }
	 }
	 /**
	  * Method to register an user in the app
	  * @param registerData RegisterData that has all de new user information to be registered 
	  * @return Returns Ok status with a new session for the user if everything goes fine or returns 403 status if there is any problem within the process
	  */
	 
	 @POST
	 @Path("/register")
	 public Response register(RegisterData registerData) {
		 logger.info("Probando existencia previa de usuario: '{}'", registerData.getLogin());
		 User user = User.find(registerData.getLogin());
		 if (user != null) {
			 logger.info("Usuario '{}' ya existe", registerData.getLogin());
			 return Response.status(403).entity("This user already exists").build();
		 } else if (registerData.getLogin().equals("SYSTEM")) {
			 logger.info("No es posible crear un usuario con el nombre de '{}' ", registerData.getLogin());
			 return Response.status(403).entity("This username is not allowed").build();
		 } else if (!Pattern.matches("^[a-zA-Z0-9_.-]*$", registerData.getLogin())) {
			 logger.info("Nombre de usuario '{}' inválido ", registerData.getLogin());
			return Response.status(403).entity("Invalid user name").build();
		 } else {
			 logger.info("Creando usuario de nombre: '{}'", registerData.getLogin());
			 user = new User(registerData.getLogin(), registerData.getPassword());
			 user.save();
			 Session session = Session.createSession(user);
			 SessionData sessionData = new SessionData(session.getToken(), session.getTimeStamp());
			 
			 return Response.ok().entity(sessionData).build();
		 }
	 }
	 /**
	  * Method that allows user to log out from the app
	  * @param tokenData TokenData with the information for the closing session 
	  * @return Returns Ok status if everything goes fine or returns 403 status if there is any problem within the process
	  */
	 @POST
	 @Path("/logout")
	 public Response logout(TokenData tokenData) {
		 Session session = Session.getSession(tokenData.getToken());
		 if (session == null) {
			 logger.info("Sesión inválida");
			 return Response.status(403).entity("Non valid session").build();
		 } else {
			 session.invalidateSession();
			 return Response.ok().build();
		 }
	 }
	 
	 /**
	  * Method that allows user to delete his account form the app
	  * @param tokenData TokenData from the session of the user that is deleting its account
	  * @return Returns Ok status if everything goes fine or returns 403 status if there is any problem within the process
	  */
	 @POST
	 @Path("/deleteUser")
	 public Response deleteUser(TokenData tokenData) {
		 Session session = Session.getSession(tokenData.getToken());
		 if (session == null) {
			 return Response.status(403).entity("Non valid session").build();
		 } else {
			 User user = session.getUser();
			 if (user == null) {
				 return Response.status(403).entity("Error finding user").build();
			 }
			 user.delete();
			 session.invalidateSession();
			 return Response.ok().build();
		 }
	 }
	 /**
	  * Get method to get the friendlist of an user
	  * @param tokenData TokenData with the information of the user making the request 
	  * @return Returns Ok status with an Arraylist<String> of friends if everything goes fine or returns 403 status if there is any problem within the process 
	  */
	 @POST
	 @Path("/getFriends")
	 public Response getFriends(TokenData tokenData) {
		 User user = Session.getSession(tokenData.getToken()).getUser();
		 if (user == null ) {
			 return Response.status(403).entity("Incorrect user").build();
		 } else {
			 return Response.ok().entity( user.getFriendListInStringFormat()).build();
		 }
	 }
	 
	 /**
	  * Method that allows user to add a friend 
	  * @param friendData FriendData that has all the information from the session of the user and the name of the new friend
	  * @return Returns Ok status if everything goes fine or returns 403 status if there is any problem within the process
	  */
	 @POST
	 @Path("/addFriend")
	 public Response addFriends(FriendData friendData) {
		 User user = Session.getSession(friendData.getToken()).getUser();
		 if (user == null) {
			 return Response.status(403).entity("Incorrect user").build();
		 } else {
			 User user2 = User.find(friendData.getFriendName());
			 if (user2 == null) {
				 return Response.status(403).entity("friend does not exist").build();
			 } else {
				 logger.info("Usuario '{}' agregando a usuario '{}'",user.getLogin(),user2.getLogin());
				 //TODO error de almacenamiento de datos
				 user.addFriend(user2);
				 user.save();
				 user2.addFriend(user);
				 user2.save();
				 return Response.ok().build();
			 }
		 }
	 }
	 
	 /**
	  * Method that allows user to delete a friend 
	  * @param friendData FriendData that has all the information from the session of the user and the name of the new friend
	  * @return Returns Ok status if everything goes fine or returns 403 status if there is any problem within the process
	  */
	 @POST
	 @Path("/deleteFriend")
	 public Response deleteFriend(FriendData friendData) {
		 User user = Session.getSession(friendData.getToken()).getUser();
		 if (user == null) {
			 return Response.status(403).entity("Incorrect user").build();
		 } else {
			 User user2 = User.find(friendData.getFriendName());
			 if (user2 == null) {
				 return Response.status(403).entity("friend does not exist").build();
			 } else {
				 logger.info("Usuario '{}' eliminando a usuario '{}'",user.getLogin(),user2.getLogin());
				 //TODO error de almacenamiento de datos
				 user.deleteFriend(user2);
				 user.save();
				 user2.deleteFriend(user);
				 user2.save();
				 return Response.ok().build();
			 }
		 }
	 }
	 
	 /**
	  * Method that allows an user to create a new room
	  * @param roomData RoomData with the current user session information, including the name and optional password for the room
	  * @return Returns Ok status if everything goes fine or returns 403 status if there is any problem within the process
	  */
	 //rooms
	 @POST
	 @Path("/createRoom")
	 public Response createRoom(RoomData roomData) {
		 Session session = Session.getSession(roomData.getToken());
		 if (session == null) {
			 logger.info("Sesión inválida");
			 return Response.status(403).entity("Non valid session").build();
		 } else {
			 User user = session.getUser();
			 if (user == null) {
				 return Response.status(403).entity("Error finding user").build();
			 }
			 logger.info("Creando sala: '{}'", roomData.getRoomName());
			 RoomManager.createRoom(roomData.getRoomName(), user, roomData.getPassword());
			 return Response.ok().build();
		 }
	 }
	 
	 /**
	  * Method that deletes a room when needed 
	  * @param roomData RoomData with all the information of the session from the user that is deleting the room
	  * @return Returns Ok status if everything goes fine or returns 403 status if there is any problem within the process
	  */
	 @POST
	 @Path("/deleteRoom")
	 public Response deleteRoom(RoomData roomData) {
		 Session session = Session.getSession(roomData.getToken());
		 if (session == null) {
			 return Response.status(403).entity("Non valid session").build();
		 } else {
			 User user = session.getUser();
			 if (user == null) {
				 return Response.status(403).entity("Error finding user").build();
			 }
			 Room room = RoomManager.getRoom(roomData.getRoomName());
			 if (room == null) {
				 return Response.status(403).entity("Error finding room").build();
			 }
			 if (!room.getOwner().getLogin().equals(user.getLogin())) {
				 return Response.status(403).entity("Not the owner of the room").build();
			 }
			 RoomManager.deleteRoom(room.getName());
			 return Response.ok().build();
		 }
	 }
	 
	 
	 
}
