package es.deusto.testing.server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.websockets.PictochatntWebSocketServer;
import es.deusto.spq.server.websockets.WebSocketData;
import es.deusto.spq.server.websockets.WebSocketHistoryData;

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
		webSocketData2 = "Join\n1\nHola";
		webSocketData3 = "Send\nHola amigos";
		webSocketData4 = "Receive\n01-01-2001\nPepe\nHola amigos";
		webSocketData5 = "History\n112-Pepe-Hola amigos\n222-Carlos-Buenas noches\n333-Antonio-A";
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
	 * Test de decode de los webSockuets
	 */
	@Test
	public void testDecode() {
		assertEquals(WebSocketData.decode(webSocketData1).encode(), "Leave\n");
		assertEquals(WebSocketData.decode("asda\n"), null);
		assertEquals(WebSocketData.decode(webSocketData2).encode(), "Join\n1\nHola");
		assertEquals(WebSocketData.decode(webSocketData3).encode(), "Send\nHola amigos");
		//assertEquals(WebSocketData.decode(webSocketData4).encode(), "Receive\n01-01-2001\nPepe\nHola amigos");
		//System.out.println(WebSocketData.decode(webSocketData3).getType() + " " + ((WebSocketSendData) WebSocketData.decode(webSocketData3)).getDate() + " " + ((WebSocketSendData) WebSocketData.decode(webSocketData3)).getUser() + " " + ((WebSocketSendData) WebSocketData.decode(webSocketData3)).getMessage());
		//System.out.println(WebSocketData.decode(webSocketData4).getType() + " " + ((WebSocketReceiveData) WebSocketData.decode(webSocketData4)).getDate() + " " + ((WebSocketReceiveData) WebSocketData.decode(webSocketData4)).getUser() + " " + ((WebSocketReceiveData) WebSocketData.decode(webSocketData4)).getMessage());
		assertEquals(WebSocketData.decode(webSocketData5).encode(),  "History\n112-Pepe-Hola amigos\n222-Carlos-Buenas noches\n333-Antonio-A");
		assertEquals(((WebSocketHistoryData) WebSocketData.decode(webSocketData5)).getMessages().get(0).getUser(), "Pepe");
	}
}
