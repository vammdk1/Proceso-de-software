package es.deusto.spq.client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
 
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import es.deusto.spq.client.Ventanas.VentanaChat;
import es.deusto.spq.client.Ventanas.VentanaMenu;
 
@WebSocket
public class PictochatntWebSocketClient {
 
    private Session session;
     
    CountDownLatch latch = new CountDownLatch(1);
 
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("Message received from server:" + message);
    }
 
    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connected to server");
        this.session=session;
        VentanaMenu.ventanaMenu.dispose();
        VentanaMenu.ventanaMenu = null;
        VentanaChat.ventanaChat = new VentanaChat();
        VentanaChat.ventanaChat.setVisible(true);
        //PONER NOMBRE DE SALA EN EL TÍTULO
        latch.countDown();
    }
     
    public void sendMessage(String str) {
        try {
            session.getRemote().sendString(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
    	System.out.println("Disconnected from server!");
        VentanaChat.ventanaChat.dispose();
        VentanaChat.ventanaChat = null;
        VentanaMenu.ventanaMenu = new VentanaMenu();
        VentanaMenu.ventanaMenu.setVisible(true);
    }
     
    
    public CountDownLatch getLatch() {
        return latch;
    }
 
}