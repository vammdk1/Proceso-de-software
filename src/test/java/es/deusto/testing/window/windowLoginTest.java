package es.deusto.testing.window;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.spq.client.PictochatntClient;
import es.deusto.spq.client.Ventanas.VentanaChat;
import es.deusto.spq.client.Ventanas.VentanaLogin;
import es.deusto.spq.client.Ventanas.VentanaMenu;
import es.deusto.spq.client.Ventanas.VentanaRegistro;
import es.deusto.spq.pojo.GetRoomData;
import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.pojo.RoomData;
import es.deusto.spq.pojo.SessionData;
import es.deusto.spq.pojo.TokenData;
import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.Resource;
import es.deusto.spq.server.jdo.User;

public class windowLoginTest {
	/**
	Resource resource;
	
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
		resource = new Resource();
		MockitoAnnotations.openMocks(this);
		PictochatntClient.instace = client;
	}
	
	
	@Test
	public void testVentanaLogin() {
		VentanaLogin ventanaLogin = new VentanaLogin();
		assertTrue(ventanaLogin.isActive());
		
		Robot bot;
		try {
			bot = new Robot();
			bot.mouseMove(20,20);
			bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			try{Thread.sleep(250);}catch(InterruptedException e){}
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ventanaLogin.dispose();
		
		
	}
	
	
	
	@Test
	public void testVentanaMenu() {
		
		ArrayList<GetRoomData> roomData = new ArrayList<>();
		GenericEntity<ArrayList<GetRoomData>> entity = new GenericEntity<ArrayList<GetRoomData>>(roomData) {};
		Response response = simulateInbound(Response.ok().entity(entity).build());
		when(client.post(eq("/getRooms"))).thenReturn(response);
		//assertEquals(roomData.size(), PictochatntClient.getActiveRooms().size());
		
		VentanaMenu ventanaMenu = new VentanaMenu();
		assertTrue(ventanaMenu.isActive());
		ventanaMenu.bCrear.doClick();
		assertTrue(ventanaMenu.datos.isVisible());
		
		ventanaMenu.bConectar.doClick();
		
		ventanaMenu.bRefrescar.doClick();
		
		ventanaMenu.Aceptar.doClick();
		
		ventanaMenu.Cancelar.doClick();
		
		assertFalse(ventanaMenu.datos.isVisible());
		
		//ventanaMenu.bSalir.getActionListeners()
		ventanaMenu.dispose();
	}
	**/
}
