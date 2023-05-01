package es.deusto.testing.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.nullable;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.pojo.RoomData;
import es.deusto.spq.pojo.SessionData;
import es.deusto.spq.pojo.TokenData;
import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.Resource;
import es.deusto.spq.server.jdo.User;

public class ResourceTest {
	Resource resource;
	@Before
	public void setUp() {
		resource = new Resource();
		
		
	}
	
	@Test
	public void testRegister() {
		RegisterData registerData = new RegisterData();
		registerData.setLogin("Paco");
		registerData.setPassword("contrasena");
		Response respuesta = resource.register(registerData);
		//System.out.println("registro de paco: "+respuesta.getEntity());
		assertEquals(Status.OK.getStatusCode(), respuesta.getStatus());
		
		respuesta = resource.register(registerData);
		assertEquals(403, respuesta.getStatus());
	}
	
	@Test
	public void testLogin() {
		RegisterData registerData = new RegisterData();
		registerData.setLogin("Marian");
		registerData.setPassword("contrasena");
		Response response = resource.register(registerData);
		SessionData sessionData = (SessionData)response.getEntity();
		
		UserData userData = new UserData();
		userData.setLogin("Marian");
		userData.setPassword("contrasena");
		Response respuesta = resource.login(userData);
		//System.out.println("asklfjdañlskfdjañlfjñasdlfj: " + User.getAll());
		assertEquals(Status.OK.getStatusCode(), respuesta.getStatus());
		
		userData.setLogin("daniel");
		respuesta = resource.login(userData);
		assertEquals(403, respuesta.getStatus());
	}
	
	
	
	
	@Test
	public void testLogout() {
		RegisterData registerData = new RegisterData();
		registerData.setLogin("Mariano");
		registerData.setPassword("contrasena");
		Response response = resource.register(registerData);
		//System.out.println("lkjasdhlkfhasldkfkasldffasf: " + response.getEntity());
		SessionData sessionData = (SessionData)response.getEntity();
		//System.out.println("init registro: " + sessionData.getToken());
		
		TokenData tokenData = new TokenData();
		tokenData.setToken(sessionData.getToken());
		Response respuesta = resource.logout(tokenData);
		System.out.println("asklfjdañlskfdjañlfjñasdlfj: " + respuesta.getEntity());
		assertEquals(Status.OK.getStatusCode(), respuesta.getStatus());
		
		tokenData.setToken("daniel");
		respuesta = resource.logout(tokenData);
		assertEquals(403, respuesta.getStatus());
	}
	
	
	@Test
	public void testDeleteUser() {
		RegisterData registerData = new RegisterData();
		registerData.setLogin("Maria");
		registerData.setPassword("contrasena");
		Response response = resource.register(registerData);
		SessionData sessionData = (SessionData)response.getEntity();
		
		TokenData tokenData = new TokenData();
		tokenData.setToken(sessionData.getToken());
		Response respuesta = resource.deleteUser(tokenData);
		System.out.println("asklfjdañlskfdjañlfjñasdlfj: " + respuesta.getEntity());
		assertEquals(Status.OK.getStatusCode(), respuesta.getStatus());
		
		tokenData.setToken("daniel");
		respuesta = resource.deleteUser(tokenData);
		assertEquals(403, respuesta.getStatus());
	}
	
	
	@Test
	public void testCreateRooms() {
		RegisterData registerData = new RegisterData();
		registerData.setLogin("Mariana");
		registerData.setPassword("contrasena");
		Response response = resource.register(registerData);
		SessionData sessionData = (SessionData)response.getEntity();
		
		RoomData roomData = new RoomData();
		roomData.setToken(sessionData.getToken());
		roomData.setPassword("contrasena");
		roomData.setRoomName("nombre");
		Response respuesta = resource.createRoom(roomData);
		assertEquals(Status.OK.getStatusCode(), respuesta.getStatus());
		
		roomData.setToken("daniel");
		respuesta = resource.createRoom(roomData);
		assertEquals(403, respuesta.getStatus());
		
	}
	
	@Test
	public void testGetRooms() {
		RegisterData registerData = new RegisterData();
		registerData.setLogin("Marina");
		registerData.setPassword("contrasena");
		Response response = resource.register(registerData);
		SessionData sessionData = (SessionData)response.getEntity();
		
		RoomData roomData = new RoomData();
		roomData.setToken(sessionData.getToken());
		roomData.setPassword("contrasena");
		roomData.setRoomName("nombre2");
		Response respuesta = resource.createRoom(roomData);
		
		roomData = new RoomData();
		roomData.setToken(sessionData.getToken());
		roomData.setPassword(null);
		roomData.setRoomName("nombre3");
		respuesta = resource.createRoom(roomData);
		
		resource.getRooms();
		
	}
	

	@Test
	public void testDeleteRoom1() {
		RegisterData registerData = new RegisterData();
		registerData.setLogin("Mario");
		registerData.setPassword("contrasena");
		Response response = resource.register(registerData);
		
		SessionData sessionData = (SessionData)response.getEntity();
		RoomData roomData = new RoomData();
		roomData.setPassword("contrasena");
		roomData.setRoomName("nombre");
		roomData.setToken(sessionData.getToken());
		
		Response respuesta = resource.createRoom(roomData);
		
		roomData.setToken("daniel");
		respuesta = resource.deleteRoom(roomData);
		assertEquals(403, respuesta.getStatus());
		
		roomData.setToken(sessionData.getToken());
		respuesta = resource.deleteRoom(roomData);
		assertEquals(Status.OK.getStatusCode(), respuesta.getStatus());
		
		respuesta = resource.deleteRoom(roomData);
		assertEquals(403, respuesta.getStatus());
		
	}
	
	@Test
	public void testDeleteRoom2() {
		RegisterData registerData = new RegisterData();
		registerData.setLogin("Marin");
		registerData.setPassword("contrasena");
		Response response = resource.register(registerData);
		
		
		SessionData sessionData = (SessionData)response.getEntity();
		RoomData roomData = new RoomData();
		roomData.setToken(sessionData.getToken());
		roomData.setPassword("contrasena");
		roomData.setRoomName("nombre");
		Response respuesta = resource.createRoom(roomData);
		
		registerData = new RegisterData();
		registerData.setLogin("Marino");
		registerData.setPassword("contrasena");
		response = resource.register(registerData);
		
		sessionData = (SessionData)response.getEntity();
		roomData.setToken(sessionData.getToken());
		
		respuesta = resource.deleteRoom(roomData);
		assertEquals(403, respuesta.getStatus());
		
	}
	
	
	
}