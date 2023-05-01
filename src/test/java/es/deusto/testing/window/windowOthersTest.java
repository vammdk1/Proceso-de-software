package es.deusto.testing.window;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import es.deusto.spq.client.Ventanas.VentanaChat;
import es.deusto.spq.client.Ventanas.VentanaRegistro;

public class windowOthersTest {
	
	
	
	

	@Test
	public void testVentanaRegister() {
		VentanaRegistro ventanaRegistro = new VentanaRegistro();
		assertTrue(ventanaRegistro.isActive());
		ventanaRegistro.dispose();
	}
	
	

	@Test
	public void testVentanaChat() {
		VentanaChat ventanaChat = new VentanaChat();
		assertTrue(ventanaChat.isActive());
		ventanaChat.dispose();
	}
	
	
	
}
