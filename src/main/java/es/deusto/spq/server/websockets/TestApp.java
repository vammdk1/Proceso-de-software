package es.deusto.spq.server.websockets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.PathParam;

import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.logic.RoomManager;

@ClientEndpoint
public class TestApp {
	 
		private final String uri= "ws://localhost:8080/rest/websocket";
			    private Session session;
			    
		public TestApp(){
				WebSocketContainer container=ContainerProvider.getWebSocketContainer();
				try {
					container.connectToServer(this, new URI(uri));
				} catch (DeploymentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}
		
		@OnOpen
		public void onOpen(@PathParam("token") String token, @PathParam("roomName") String roomName) throws IOException, EncodeException {
			System.out.println("Entra");
		}
		
		
		@OnMessage
		public void onMessage(String message) throws IOException {
			
		}
		
		@OnClose
		public void onClose(Session session) throws IOException, EncodeException {
			System.out.println("Sale");
		}
		
		@OnError
		public void onError(Session session, Throwable throwable) {
			throwable.printStackTrace();
		}
		
		public static void main(String args[]) {
			new TestApp();
		}
			   
}
