package es.deusto.spq.server.websockets;

import java.io.IOException;
import java.util.HashSet;

import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import es.deusto.spq.pojo.Message;
import es.deusto.spq.pojo.WebSocketData;
import es.deusto.spq.pojo.WebSocketPaintData;
import es.deusto.spq.pojo.WebSocketSendData;
import es.deusto.spq.pojo.WebSocketSessionData;
import es.deusto.spq.pojo.WebSocketPaintData.Mode;
import es.deusto.spq.server.logic.RoomManager;

@WebSocket
public class PictochatntWebSocketServer {
 
	HashSet<WebSocketSessionData> connections = new HashSet<WebSocketSessionData>();
	
	/**
	 * This method sets the behavior the server musts follow when receiving a message
	 * @param session
	 * @param message
	 * @throws IOException
	 */
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("Message received:" + message);
        WebSocketSessionData sessionData = new WebSocketSessionData(session);
        if (session.isOpen()) {
        	WebSocketData data = WebSocketData.decode(message);
        	
            if (data == null) {
                return;
            }

        	if (data.getType().equals("Send")) {
        		WebSocketSendData sendData = (WebSocketSendData) data;
        		if (sessionData.getRoom() != null && sessionData.getUser() != null) {
        			Message m = new Message(sendData.getMessage(), sessionData.getUser().getLogin());
        			sessionData.getRoom().addMessage(m);
        		}
        	} else if (data.getType().equals("Paint")) {
                WebSocketPaintData paintData = (WebSocketPaintData) data;
                if (sessionData.getRoom() != null && sessionData.getUser() != null) {
                    sessionData.getRoom().paint(paintData.getX(), paintData.getY(), paintData.getMode() == Mode.Erase);
                }
            }
        	
            String response = message.toUpperCase();
            session.getRemote().sendString(response);
        }
    }
 
    /**
     * This method sets the behavior the server musts follow when connecting to a room
     * @param session
     * @throws IOException
     */
    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
        System.out.println(session.getRemoteAddress().getHostString() + " connected!");
        //System.out.println(session.getUpgradeRequest().getRequestURI() + " connected!");
        
        WebSocketSessionData data = new WebSocketSessionData(session);
        
        if (connections.contains(data)) {
        	session.close(new CloseStatus(StatusCode.BAD_DATA, "Token already connected!"));
        	return;
        }
        
        if (data.getUser() == null) {
        	session.close(new CloseStatus(StatusCode.BAD_DATA, "Invalid token"));
        	return;
        }
        
        if (data.getRoom() == null) {
        	session.close(new CloseStatus(StatusCode.BAD_DATA, "Invalid room name"));
        	return;
        }
        
        if (data.getRoom().getPassword() != null) {
        	if (!data.getRoom().getPassword().equals(data.getPassword())) {
        		session.close(new CloseStatus(StatusCode.BAD_DATA, "Incorrect password"));
            	return;
        	}
        }
        
        if (!RoomManager.addUserRoom(data.getUser(), data.getRoom().getName(), session)) {
        	session.close(new CloseStatus(StatusCode.BAD_DATA, "Couldn't join room"));
        	return;
        }
        connections.add(data);
    }
 
    /**
     * This method sets the behavior the server musts follow when leaving a room
     * @param session
     * @param status
     * @param reason
     */
    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        System.out.println(session.getRemoteAddress().getHostString() + " closed!");
        //System.out.println(session.getUpgradeRequest().getRequestURI() + " disconnected!");
        WebSocketSessionData sessionData = new WebSocketSessionData(session);
        if (sessionData.getRoom() != null && sessionData.getUser() != null) {
        	RoomManager.deleteUserRoom(sessionData.getUser(), sessionData.getRoom().getName());
        }
        connections.remove(sessionData);
    }
 
}