package es.deusto.testing.cliente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mysql.cj.xdevapi.Client;

import es.deusto.spq.client.PictochatntClient;
import es.deusto.spq.pojo.GetRoomData;
import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.pojo.RoomData;
import es.deusto.spq.pojo.SessionData;
import es.deusto.spq.pojo.TokenData;
import es.deusto.spq.pojo.UserData;
import net.bytebuddy.asm.Advice.Argument;

public class ClientTest {
	
	UserData user ;
	
	@SuppressWarnings("unchecked")
	private static <T> T readEntity(Response realResponse, Class<T> t) {
		return (T)realResponse.getEntity();
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T readEntity(Response realResponse, GenericType<T> t) {
		return (T)realResponse.getEntity();
	}
	
	public static Response simulateInbound(Response asOutbound) {
		Response toReturn = spy(asOutbound);//redirecciones
		doAnswer(AdditionalAnswers.answer((Class<?> type) -> readEntity(toReturn, type))).when(toReturn).readEntity(ArgumentMatchers.<Class<?>>any());
		doAnswer(AdditionalAnswers.answer((GenericType<?> type) -> readEntity(toReturn, type))).when(toReturn).readEntity(ArgumentMatchers.<GenericType<?>>any());
		return toReturn;
	}
	
	@Mock
	PictochatntClient client;
	
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		PictochatntClient.instace = client;
		user = new UserData();
		user.setLogin("Nombre");
		user.setPassword("Qw1234567890");
	}
	
	@Test
	public void testRegisterUser() {
		user= new UserData();
		SessionData sessiondata = new SessionData();
		sessiondata.setToken("patata");
		Response response = simulateInbound(Response.ok().entity(sessiondata).build());
		when(client.post(eq("/register"), any(RegisterData.class))).thenReturn(response);
		assertEquals(true, PictochatntClient.register(user.getLogin(), user.getPassword()));
	}
	
	@Test
	public void testIncioUserTrue() {
		SessionData sessiondata = new SessionData();
		sessiondata.setToken("pataat");
		Response response = simulateInbound(Response.ok().entity(sessiondata).build());
		when(client.post(eq("/login"), any(UserData.class))).thenReturn(response);
		assertEquals(true, PictochatntClient.login(user.getLogin(), user.getPassword()));
	}
	
	@Test
	public void testIncioUserFalse() {
		SessionData sessiondata = new SessionData();
		sessiondata.setToken("pataat");
		Response response = simulateInbound(Response.status(404).entity(sessiondata).build());
		when(client.post(eq("/login"), any(UserData.class))).thenReturn(response);
		assertEquals(false, PictochatntClient.login("Nombre2", "Qw1234567890"));
	}
	
	@Test
	public void testLogOutUserNoToken() {
		PictochatntClient.token = null;
		assertEquals(false, PictochatntClient.logout());
	}
	
	@Test
	public void testLogOutUser() {
		PictochatntClient.token="patata";
		TokenData tokendata = new TokenData();
		tokendata.setToken(PictochatntClient.token);
		Response response = simulateInbound(Response.ok().entity(tokendata).build());
		when(client.post(eq("/logout"), any(TokenData.class))).thenReturn(response);
		assertEquals(true, PictochatntClient.logout());
	}
	
	@Test
	public void testDeleteUserNoToken() {
		PictochatntClient.token = null;
		assertEquals(false, PictochatntClient.deleteUser());
	}

	@Test
	public void testDeleteUser() {
		PictochatntClient.token="patata";
		TokenData tokendata = new TokenData();
		tokendata.setToken(PictochatntClient.token);
		Response response = simulateInbound(Response.ok().entity(tokendata).build());
		when(client.post(eq("/deleteUser"), any(TokenData.class))).thenReturn(response);
		assertEquals(true, PictochatntClient.deleteUser());
	}
	
	@Test
	public void testCreateRoomNoToken() {
		PictochatntClient.token = null;
		assertEquals(false, PictochatntClient.createRoom("nombre",""));
		assertEquals(false, PictochatntClient.createRoom("nombre","asdadasdasd"));
	}
	
	@Test
	public void testCreatePublicRoom() {
		PictochatntClient.token = "patata";
		RoomData roomData= new RoomData();
		roomData.setRoomName("prueba");
		roomData.setToken(PictochatntClient.token);
		Response response = simulateInbound(Response.ok().entity(roomData).build());
		when(client.post(eq("/createRoom"), any(roomData.getClass()))).thenReturn(response);
		assertEquals(true, PictochatntClient.createRoom(roomData.getRoomName(),""));
	}
	
	@Test
	public void testCreatePrivateRoom() {
		PictochatntClient.token = "patata";
		RoomData roomData= new RoomData();
		roomData.setRoomName("prueba2");
		roomData.setPassword("123456789");
		roomData.setToken(PictochatntClient.token);
		Response response = simulateInbound(Response.ok().entity(roomData).build());
		when(client.post(eq("/createRoom"), any(roomData.getClass()))).thenReturn(response);
		assertEquals(true, PictochatntClient.createRoom(roomData.getRoomName(),""));
	}
	
	@Test
	public void testDeleteRoomNotoken() {
		PictochatntClient.token = null;
		assertEquals(false, PictochatntClient.deleteRoom("x"));
	}
	
	@Test
	public void testDeleteRoom() {
		PictochatntClient.token = "patata";
		RoomData roomData= new RoomData();
		roomData.setRoomName("prueba2");
		roomData.setPassword("123456789");
		roomData.setToken(PictochatntClient.token);
		Response response = simulateInbound(Response.ok().entity(roomData).build());
		when(client.post(eq("/deleteRoom"), any(roomData.getClass()))).thenReturn(response);
		assertEquals(true, PictochatntClient.deleteRoom(roomData.getRoomName()));
	}
	
	@Test
	public void testGetRooms() {
		ArrayList<GetRoomData> roomData = new ArrayList<>();
		GenericEntity<ArrayList<GetRoomData>> entity = new GenericEntity<ArrayList<GetRoomData>>(roomData) {};
		Response response = simulateInbound(Response.ok().entity(entity).build());
		when(client.post(eq("/getRooms"))).thenReturn(response);
		assertEquals(roomData.size(), PictochatntClient.getActiveRooms().size());
	}
	

	
	

}
