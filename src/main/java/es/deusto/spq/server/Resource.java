package es.deusto.spq.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {
	

	protected static final Logger logger = LogManager.getLogger();

	// private int cont = 0;
	// private PersistenceManager pm=null;
	// private Transaction tx=null;

	public Resource() {
		System.out.println("hola");
		// PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		// this.pm = pmf.getPersistenceManager();
		// this.tx = pm.currentTransaction();
	}

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
}
