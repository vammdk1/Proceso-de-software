package es.deusto.spq.server;

import java.util.ArrayList; 
import java.util.Map;

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
	 
	 @POST
	 @Path("/login")
	 public Response login(UserData userData) {
		 User user = User.find(userData.getLogin());
		 if (user == null || !user.isPasswordCorrect(userData.getPassword())) {
			 return Response.status(403).entity("Incorrect username or password").build();
		 } else {
			 Session session = Session.createSession(user);
			 SessionData sessionData = new SessionData(session.getToken(), session.getTimeStamp());
			 return Response.ok().entity(sessionData).build();
		 }
	 }
	 
	 @POST
	 @Path("/register")
	 public Response register(RegisterData registerData) {
		 User user = User.find(registerData.getLogin());
		 if (user != null) {
			 return Response.status(403).entity("This user already exists").build();
		 } else {
			 user = new User(registerData.getLogin(), registerData.getPassword());
			 user.save();
			 Session session = Session.createSession(user);
			 SessionData sessionData = new SessionData(session.getToken(), session.getTimeStamp());
			 return Response.ok().entity(sessionData).build();
		 }
	 }
	 
	 @POST
	 @Path("/logout")
	 public Response logout(TokenData tokenData) {
		 Session session = Session.getSession(tokenData.getToken());
		 if (session == null) {
			 return Response.status(403).entity("Non valid session").build();
		 } else {
			 session.invalidateSession();
			 return Response.ok().build();
		 }
	 }
	 
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
	 
	 @POST
	 @Path("/getFriends")
	 public Response getFriends(TokenData tokenData) {
		 User user = Session.getSession(tokenData.getToken()).getUser();
		 if (user == null ) {
			 return Response.status(403).entity("Incorrect user").build();
		 } else {
			 return Response.ok().entity( user.getFriendsList()).build();
		 }
	 }
	 
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
				 user.addFriend(user2.getLogin());
				 user.save();
				 user2.addFriend(user.getLogin());
				 user2.save();
				 return Response.ok().build();
			 }
		 }
	 }
	 
	 //rooms
	 
	 @POST
	 @Path("/createRoom")
	 public Response createRoom(RoomData roomData) {
		 Session session = Session.getSession(roomData.getToken());
		 if (session == null) {
			 return Response.status(403).entity("Non valid session").build();
		 } else {
			 User user = session.getUser();
			 if (user == null) {
				 return Response.status(403).entity("Error finding user").build();
			 }
			 RoomManager.createRoom(roomData.getRoomName(), user, roomData.getPassword());
			 return Response.ok().build();
		 }
	 }
	 
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
