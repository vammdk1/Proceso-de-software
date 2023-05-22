package es.deusto.spq.client;

import java.io.IOException; 
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import es.deusto.spq.client.Ventanas.VentanaChat;
import es.deusto.spq.client.Ventanas.VentanaMenu;
import es.deusto.spq.pojo.Message;
import es.deusto.spq.pojo.WebSocketData;
import es.deusto.spq.pojo.WebSocketHistoryData;
import es.deusto.spq.pojo.WebSocketReceiveData;
import es.deusto.spq.pojo.WebSocketSendData;
 
@WebSocket
public class PictochatntWebSocketClient {
 
	private Session session;
    private boolean left;
     
    CountDownLatch latch = new CountDownLatch(1);
 
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        WebSocketData data = WebSocketData.decode(message);
        
        if (data.getType().equals("Receive")) {
        	WebSocketReceiveData receiveData = (WebSocketReceiveData) data;
        	VentanaChat.ventanaChat.addMessage(receiveData.getUser(), receiveData.getMessage(), receiveData.getDate());
        } else if (data.getType().equals("History")) {
        	WebSocketHistoryData historyData = (WebSocketHistoryData) data;
        	for (Message m : historyData.getMessages()) {
        		VentanaChat.ventanaChat.addMessage(m.getUser(), m.getText(), m.getTimestamp());
        	}
        } else if (data.getType().equals("Leave")) {
        	left = true;
        }
    }
 
    @OnWebSocketConnect
    public void onConnect(Session session) {
    	this.session = session;
        System.out.println("Connected to server");
        VentanaMenu.ventanaMenu.dispose();
        VentanaMenu.ventanaMenu = null;
        VentanaChat.ventanaChat = new VentanaChat();
        VentanaChat.ventanaChat.setVisible(true);
        left = false;
        latch.countDown();
    }
    
    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
    	System.out.println("Disconnected from server!");
    	if (!left) {
    		JOptionPane.showMessageDialog(null, "Has sido expulsado de la sala.", "Error", JOptionPane.ERROR_MESSAGE);
    	}
        VentanaChat.ventanaChat.dispose();
        VentanaChat.ventanaChat = null;
        VentanaMenu.ventanaMenu = new VentanaMenu();
        VentanaMenu.ventanaMenu.setVisible(true);
        PictochatntClient.leaveRoom();
        session = null;
    }
    
    public boolean sendMessage(String message) {
    	WebSocketSendData data = new WebSocketSendData(message); 
    	if (session != null && session.isOpen()) {
	    	try {
				session.getRemote().sendString(data.encode());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	    	return true;
    	}
    	return false;
    }
     
    
    public CountDownLatch getLatch() {
        return latch;
    }
    
    public void clientExited() {
    	left = true;
    }
 
}