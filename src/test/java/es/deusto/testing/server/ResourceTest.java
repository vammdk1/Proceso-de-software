package es.deusto.testing.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.server.Resource;

public class ResourceTest {
	
	@Mock
	Resource resource;
	
	Response response;
	
	@Mock
    private PersistenceManager persistenceManager;

    @Mock
    private Transaction transaction;
    
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resource = mock(Resource.class);
        //resource = new Resource();
        
    }
    @Test
    public void testRegisterMock() {
        // prepare mock Persistence Manager to return User
        RegisterData registerData = new RegisterData();
        registerData.setLogin("test-login");
        registerData.setPassword("passwd");

        // create a response object
        Response response = mock(Response.class);
        when(response.getStatusInfo()).thenReturn(Response.Status.OK);

        // simulate that
        when(resource.register(any(RegisterData.class))).thenReturn(response);

        // call tested method
        Response result = resource.register(registerData);
        System.out.println(result.getStatusInfo());

        // verify the password is set to "passwd"
        ArgumentCaptor<RegisterData> registerDataCaptor = ArgumentCaptor.forClass(RegisterData.class);
        verify(resource).register(registerDataCaptor.capture());
        assertEquals("passwd", registerDataCaptor.getValue().getPassword());

        // check expected response
        assertEquals(response, result);
        assertEquals(Response.Status.OK, result.getStatusInfo());
    }
		/**
		@Test
		public void testLoginMock() {
			RegisterData registerData = new RegisterData();
			registerData.setLogin("Marian");
			registerData.setPassword("contrasena");
			
			User user = spy(User.class);
			when(persistenceManager.getObjectById(User.class, registerData.getLogin())).thenReturn(user);
			
			Response response = resource.register(registerData);
			
			SessionData sessionData = spy(SessionData.class);
			when(persistenceManager.getObjectById(SessionData.class,sessionData)).thenReturn(sessionData);
			Response responseS = (Response)response.getEntity();
			//SessionData sessionData = (SessionData)response.getEntity();

			UserData userData = new UserData();
			userData.setLogin("Marian");
			userData.setPassword("contrasena");
			
			User user2 = spy(User.class);
			when(persistenceManager.getObjectById(User.class, userData.getLogin())).thenReturn(user2);
			
			Response respuesta = resource.login(userData);
			//System.out.println("asklfjdañlskfdjañlfjñasdlfj: " + User.getAll());
			assertEquals(403, respuesta.getStatus());
			
			userData.setLogin("daniel");
			respuesta = resource.login(userData);
			assertEquals(403, respuesta.getStatus());
			
		}**/
	
// ------- Pruebas de integración -----------------//
	/**
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
		
		RegisterData registerData2 = new RegisterData();
		registerData2.setLogin("victor");
		registerData2.setPassword("contrasenna");
		Response response2 = resource.register(registerData2);
		SessionData sessionData2 = (SessionData)response2.getEntity();
		
		RoomManager roomManager = new RoomManager();
		roomManager.addUserRoom(new User(registerData2.getLogin(),registerData2.getPassword()), roomData.getRoomName());
		roomManager.addUserRoom(new User(registerData2.getLogin(),registerData2.getPassword()), "patata");
		assertEquals(registerData2.getLogin(), roomManager.getRoom(roomData.getRoomName()).getUsers().get(0).getLogin());
		roomManager.deleteUserRoom(new User(registerData2.getLogin(),registerData2.getPassword()), "patata");
		
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

	**/
	
}
