package es.deusto.spq.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
    private ArrayList<Message> pendingMessages = new ArrayList<Message>();;
     
    CountDownLatch latch = new CountDownLatch(1);
 
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        WebSocketData data = WebSocketData.decode(message);
        
        if (data == null) {
        	return;
        }
        
        if (data.getType().equals("Receive")) {
        	WebSocketReceiveData receiveData = (WebSocketReceiveData) data;
        	if (VentanaChat.ventanaChat == null) {
        		pendingMessages.add(new Message(receiveData.getMessage(), receiveData.getUser(), receiveData.getDate()));
        	} else {
        		VentanaChat.ventanaChat.addMessage(receiveData.getUser(), receiveData.getMessage(), receiveData.getDate());
        	}
        } else if (data.getType().equals("History")) {
        	WebSocketHistoryData historyData = (WebSocketHistoryData) data;
        	if (VentanaChat.ventanaChat == null) {
        		pendingMessages.addAll(historyData.getMessages());
        	} else {
        		for (Message m : historyData.getMessages()) {
        			VentanaChat.ventanaChat.addMessage(m.getUser(), m.getText(), m.getTimestamp());
        		}
        	}
            //TODO: restaurar historial cliente
        } else if (data.getType().equals("Paint")) {
            //TODO: pintar cliente
        } else if (data.getType().equals("Leave")) {
        	left = true;
        }
    }
 
    @OnWebSocketConnect
    public void onConnect(Session session) {
    	this.session = session;
        System.out.println("Connected to server");
        SwingUtilities.invokeLater(() -> {
        	VentanaMenu.ventanaMenu.dispose();
            VentanaMenu.ventanaMenu = null;
            VentanaChat.ventanaChat = new VentanaChat();
            VentanaChat.ventanaChat.setVisible(true);
            for (Message m : pendingMessages) {
            	VentanaChat.ventanaChat.addMessage(m.getUser(), m.getText(), m.getTimestamp());
            }
            pendingMessages.clear();
        });
        left = false;
        latch.countDown();
    }
    
    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
    	System.out.println("Disconnected from server!");
    	System.out.println(status + " " + reason);
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
    
    public boolean isConnected() {
    	return session != null && session.isOpen();
    }
     
    
    public CountDownLatch getLatch() {
        return latch;
    }
    
    public void clientExited() {
    	left = true;
    }
 
}