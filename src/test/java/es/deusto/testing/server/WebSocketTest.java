package es.deusto.testing.server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.websockets.WebSocket;
import es.deusto.spq.server.websockets.WebSocketData;
import es.deusto.spq.server.websockets.WebSocketHistoryData;
import es.deusto.spq.server.websockets.WebSocketJoinData;
import es.deusto.spq.server.websockets.WebSocketReceiveData;
import es.deusto.spq.server.websockets.WebSocketSendData;

public class WebSocketTest {
	WebSocket webSocket;
	String webSocketData1;
	String webSocketData2;
	String webSocketData3;
	String webSocketData4;
	String webSocketData5;
	WebSocketJoinData wSJD;
	
	@Before
	public void setUp() {
		webSocket = new WebSocket();
		webSocketData1 = "Leave\n";
		webSocketData2 = "Join\n1\nHola";
		webSocketData3 = "Send\n";
		webSocketData4 = "Receive\n";
		webSocketData5 = "History\n";
		wSJD = (WebSocketJoinData) WebSocketData.decode(webSocketData2);
	}
	
	@Test
	public void testWebSocket() {
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
	}
	
	
	@Test
	public void testDecode() {
		assertEquals(WebSocketData.decode(webSocketData1).encode(), "Leave\n");
		assertEquals(WebSocketData.decode("asda\n"), null);
		wSJD.setRoomName("Pepe");
		wSJD.setToken("A");		
		assertEquals(wSJD.getRoomName(), "Pepe");
		assertEquals(wSJD.getToken(), "A");
		assertEquals(WebSocketData.decode(webSocketData2).encode(), "Join\n1\nHola");
		wSJD.setType("Leave");
		assertEquals(wSJD.getType(), "Leave");
		assertEquals(WebSocketData.decode(webSocketData3), null);
		assertEquals(WebSocketData.decode(webSocketData4), null);
		assertEquals(WebSocketData.decode(webSocketData5), null);
		assertEquals(new WebSocketSendData().encode(), null);
		assertEquals(new WebSocketReceiveData().encode(), null);
		assertEquals(new WebSocketHistoryData().encode(), null);
	}
}
