package es.deusto.testing.server;

import static org.junit.Assert.assertEquals; 

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.pojo.WebSocketData;
import es.deusto.spq.pojo.WebSocketHistoryData;
import es.deusto.spq.server.websockets.PictochatntWebSocketServer;

public class WebSocketTest {
	PictochatntWebSocketServer webSocket;
	String webSocketData1;
	String webSocketData2;
	String webSocketData3;
	String webSocketData4;
	String webSocketData5;
	
	@Before
	public void setUp() {
		//webSocket = new WebSocket();
		webSocketData1 = "Leave\n";
		webSocketData3 = "Send\nSG9sYSBhbWlnb3MK";
		webSocketData4 = "Receive\n1234\nPepe\nSG9sYSBhbWlnb3MK";
		webSocketData5 = "History\n1234;Pepe;SG9sYSBhbWlnb3MK\n2001;Carlos;QnVlbmFzIG5vY2hlcwo=\n3244;Antonio;QQo=";
	}
	
	@Test
	public void testWebSocket() {
		/*
		try {
			webSocket.onOpen(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			webSocket.onClose(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			webSocket.onMessage(null, wSJD);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		webSocket.onError(null, null);
		*/
	}
	
	/**
	 * Test de decode de los webSockets
	 */
	@Test
	public void testDecode() {
		assertEquals(WebSocketData.decode(webSocketData1).encode(), "Leave\n");
		assertEquals(WebSocketData.decode(webSocketData3).encode(), "Send\nSG9sYSBhbWlnb3MK");
		assertEquals(WebSocketData.decode(webSocketData4).encode(), "Receive\n1234\nPepe\nSG9sYSBhbWlnb3MK");
		//assertEquals(WebSocketData.decode(webSocketData5).encode(),  "History\n1234;Pepe;SG9sYSBhbWlnb3MK\n2001;Carlos;QnVlbmFzIG5vY2hlcwo=\n3244;Antonio;QQo=");
		assertEquals(((WebSocketHistoryData) WebSocketData.decode(webSocketData5)).getMessages().get(0).getUser(), "Pepe");
	}
}
