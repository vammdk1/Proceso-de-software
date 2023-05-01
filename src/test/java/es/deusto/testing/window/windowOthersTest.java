package es.deusto.testing.window;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import es.deusto.spq.client.Ventanas.VentanaChat;
import es.deusto.spq.client.Ventanas.VentanaRegistro;

public class windowOthersTest {
	
	
	/*
	@Test
	public void test_myMethod() {
	    MyClass toTest = mock(MyClass.class);

	    //Call real method we want to test     
	    when(toTest.myMethod()).doCallRealMethod();

	    //Mock away JOptionPane
	    when(toTest.getValueFromDialog()).thenReturn("HELLO JUNIT");
 
	    //Perform actual test code
	    assertFalse(toTest.myMethod());
	}
	*/
	
	@Test
	public void testVentanaRegister() {
		
		
		
		VentanaRegistro ventanaRegistro = new VentanaRegistro();
		
		
		
		assertTrue(ventanaRegistro.isActive());
		
		//doReturn("hola").when(ventanaRegistro.ensenarJOption(anyString()));
		
		ventanaRegistro.loginButton.doClick();
		
		ventanaRegistro.passwordField.setText("a");
		ventanaRegistro.usernameField.setText("pablo");
		
		ventanaRegistro.loginButton.doClick();
		
		ventanaRegistro.passwordField.setText("aaaaaaaa");
		
		ventanaRegistro.loginButton.doClick();
		
		ventanaRegistro.passwordField.setText("Aaaaaaaaa128j");
		
		ventanaRegistro.loginButton.doClick();
		
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
		
		
		ventanaRegistro.dispose();
	}
	
	

	@Test
	public void testVentanaChat() {
		VentanaChat ventanaChat = new VentanaChat();
		assertTrue(ventanaChat.isActive());
		
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
		
		ventanaChat.dispose();
	}
	
	
	
}
