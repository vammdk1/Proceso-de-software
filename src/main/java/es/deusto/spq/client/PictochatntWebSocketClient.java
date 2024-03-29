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
import es.deusto.spq.pojo.WebSocketPaintData;
import es.deusto.spq.pojo.WebSocketReceiveData;
import es.deusto.spq.pojo.WebSocketSendData;
import es.deusto.spq.pojo.WebSocketPaintData.Mode;
 
@WebSocket
public class PictochatntWebSocketClient {
 
	private Session session;
    private boolean left;
    private ArrayList<Message> pendingMessages = new ArrayList<Message>();
    private byte[] pendingImage;
     
    CountDownLatch latch = new CountDownLatch(1);
 
    /**
     * This method handles the message sending for the WebSockets
     * @param session
     * @param message
     * @throws IOException
     */
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
        		pendingImage = historyData.getImage();
        	} else {
        		for (Message m : historyData.getMessages()) {
        			VentanaChat.ventanaChat.addMessage(m.getUser(), m.getText(), m.getTimestamp());
        		}
        		VentanaChat.ventanaChat.getImageHistory(historyData.getImage());
        	}
   
        } else if (data.getType().equals("Paint")) {
        	WebSocketPaintData paintData = (WebSocketPaintData) data;
        	if (VentanaChat.ventanaChat != null) {
        		if (paintData.getMode() == Mode.Erase) {
        			VentanaChat.ventanaChat.paint(paintData.getX(), paintData.getY(), true);
        		} else {
        			VentanaChat.ventanaChat.paint(paintData.getX(), paintData.getY(), false);
        		}
        	}
        } else if (data.getType().equals("Leave")) {
        	left = true;
        }
    }
 
   /**
    * This method handles the connection establishment for the WebSockets
    * @param session
    */
    @OnWebSocketConnect
    public void onConnect(Session session) {
    	this.session = session;
        System.out.println("Connected to server");
        SwingUtilities.invokeLater(() -> {
        	VentanaMenu.ventanaMenu.dispose();
            VentanaMenu.ventanaMenu = null;
            VentanaChat.ventanaChat = new VentanaChat();
            VentanaChat.ventanaChat.setVisible(true);
            VentanaChat.ventanaChat.setRoomName(PictochatntClient.getCurrentRoomName());
            for (Message m : pendingMessages) {
            	VentanaChat.ventanaChat.addMessage(m.getUser(), m.getText(), m.getTimestamp());
            }
            pendingMessages.clear();
            if (pendingImage != null) {
            	VentanaChat.ventanaChat.getImageHistory(pendingImage);
            	pendingImage = null;
            }
        });
        left = false;
        latch.countDown();
    }
    
    /**
     * This method handles the connection closing for the WebSockets
     * @param session
     * @param status
     * @param reason
     */
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
    
    
    /**
     * This method sends a message to the server remote
     * @param message
     * @return
     */
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
    
    /**
     * This method sends the paint data encoded to the server remote
     * @param x
     * @param y
     * @param erase
     * @return
     */
    public boolean sendPaintMessage(int x, int y, boolean erase) {
    	WebSocketPaintData paintData;
    	if (erase) {
    		paintData = new WebSocketPaintData(x, y, Mode.Erase);
    	} else {
    		paintData = new WebSocketPaintData(x, y, Mode.Paint);
    	}
    	
    	if (session != null && session.isOpen()) {
    		try {
    			session.getRemote().sendString(paintData.encode());
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