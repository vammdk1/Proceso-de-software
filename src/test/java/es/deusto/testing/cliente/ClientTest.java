package es.deusto.testing.cliente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

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
import es.deusto.spq.pojo.RegisterData;
import es.deusto.spq.pojo.SessionData;
import es.deusto.spq.pojo.UserData;
import net.bytebuddy.asm.Advice.Argument;

public class ClientTest {
	
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
	}
	
	@Test
	public void testRegisterUser() {
		SessionData sessiondata = new SessionData();
		sessiondata.setToken("patata");
		Response response = simulateInbound(Response.ok().entity(sessiondata).build());
		when(client.post(eq("/register"), any(RegisterData.class))).thenReturn(response);
		assertEquals(true, PictochatntClient.register("Nombre", "Qw1234567890"));
	}
	
	@Test
	public void testIncioUser() {
		SessionData sessiondata = new SessionData();
		sessiondata.setToken("pataat");
		Response response = simulateInbound(Response.ok().entity(sessiondata).build());
		when(client.post(eq("/login"), any(UserData.class))).thenReturn(response);
		assertEquals(true, PictochatntClient.login("Nombre", "Qw1234567890"));
	}
}
