package es.deusto.spq.server;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.pojo.SessionData;
import es.deusto.spq.pojo.TokenData;
import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.data.Session;
import es.deusto.spq.server.jdo.User;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {
	

	protected static final Logger logger = LogManager.getLogger();


	// @POST
	// @Path("/sayMessage")
	// public Response sayMessage(DirectMessage directMessage) {
	// 	User user = null;
	// 	try{
	// 		tx.begin();
	// 		logger.info("Creating query ...");
			
	// 		try (Query<?> q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE login == \"" + directMessage.getUserData().getLogin() + "\" &&  password == \"" + directMessage.getUserData().getPassword() + "\"")) {
	// 			q.setUnique(true);
	// 			user = (User)q.execute();
				
	// 			logger.info("User retrieved: {}", user);
	// 			if (user != null)  {
	// 				Message message = new Message(directMessage.getMessageData().getMessage());
	// 				user.getMessages().add(message);
	// 				pm.makePersistent(user);					 
	// 			}
	// 		} catch (Exception e) {
	// 			e.printStackTrace();
	// 		}
	// 		tx.commit();
	// 	} finally {
	// 		if (tx.isActive()) {
	// 			tx.rollback();
	// 		}
	// 	}
		
	// 	if (user != null) {
	// 		cont++;
	// 		logger.info(" * Client number: {}", cont);
	// 		MessageData messageData = new MessageData();
	// 		messageData.setMessage(directMessage.getMessageData().getMessage());
	// 		return Response.ok(messageData).build();
	// 	} else {
	// 		return Response.status(Status.BAD_REQUEST).entity("Login details supplied for message delivery are not correct").build();
	// 	}
	// }
	
	// @POST
	// @Path("/register")
	// public Response registerUser(UserData userData) {
	// 	try
    //     {	
    //         tx.begin();
    //         logger.info("Checking whether the user already exits or not: '{}'", userData.getLogin());
	// 		User user = null;
	// 		try {
	// 			user = pm.getObjectById(User.class, userData.getLogin());
	// 		} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
	// 			logger.info("Exception launched: {}", jonfe.getMessage());
	// 		}
	// 		logger.info("User: {}", user);
	// 		if (user != null) {
	// 			logger.info("Setting password user: {}", user);
	// 			user.setPassword(userData.getPassword());
	// 			logger.info("Password set user: {}", user);
	// 		} else {
	// 			logger.info("Creating user: {}", user);
	// 			user = new User(userData.getLogin(), userData.getPassword());
	// 			pm.makePersistent(user);					 
	// 			logger.info("User created: {}", user);
	// 		}
	// 		tx.commit();
	// 		return Response.ok().build();
    //     }
    //     finally
    //     {
    //         if (tx.isActive())
    //         {
    //             tx.rollback();
    //         }
      
	// 	}
	// }

	 @POST
	 @Path("/hello")
	 public Response sayHello() {
		System.out.println("prueba");
		 return Response.ok("Hello world!").build();
	 }
	 
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
	 
}
