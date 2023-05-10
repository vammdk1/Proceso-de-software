package es.deusto.spq.server.websockets;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.ContainerProvider;
import javax.websocket.EncodeException;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.logic.RoomManager;

@ServerEndpoint(value = "/websocket")
public class WebSocket {
	
	private Session session;
	private static Set<WebSocket> webSockets = new CopyOnWriteArraySet<>();
	private MessageHandler messageHandler;
	
	public WebSocket() {
		try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI("ws://localhost:8080/rest/resource"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public WebSocket(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	@OnOpen
	public void onOpen(@PathParam("token") String token, @PathParam("roomName") String roomName) throws IOException, EncodeException {
		//this.session = session;
		webSockets.add(this);
		RoomManager.getRoom(roomName).joinUser(User.find(es.deusto.spq.server.data.Session.getSession(token).getUser().getLogin()));
		WebSocketData joinData = new WebSocketJoinData(token, roomName);
		broadcast(joinData);
		this.session.getAsyncRemote().sendText("Aaa");
	}
	
	
	@OnMessage
	public void onMessage(String message) throws IOException {
		
	}
	
	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		webSockets.remove(this);
		WebSocketData leaveData = new WebSocketLeaveData();
		broadcast(leaveData);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		throwable.printStackTrace();
	}
	
	public void addMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}
	
	private static void broadcast(WebSocketData webSocketData) 
		      throws IOException, EncodeException {
		 
		        webSockets.forEach(endpoint -> {
		            synchronized (endpoint) {
		                try {
		                    endpoint.session.getBasicRemote().
		                      sendObject(webSocketData);
		                } catch (IOException | EncodeException e) {
		                    e.printStackTrace();
		                }
		            }
		        });
		    }
	
	public static interface MessageHandler {

        public void handleMessage(String message);
    }
}
