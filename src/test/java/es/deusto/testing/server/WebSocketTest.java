package es.deusto.testing.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.websockets.WebSocket;
import es.deusto.spq.server.websockets.WebSocketData;
import es.deusto.spq.server.websockets.WebSocketHistoryData;
import es.deusto.spq.server.websockets.WebSocketJoinData;
import es.deusto.spq.server.websockets.WebSocketLeaveData;
import es.deusto.spq.server.websockets.WebSocketReceiveData;
import es.deusto.spq.server.websockets.WebSocketSendData;

public class WebSocketTest {
	WebSocket webSocket;
	String webSocketData1;
	String webSocketData2;
	String webSocketData3;
	String webSocketData4;
	String webSocketData5;
	
	@Before
	public void setUp() {
		webSocket = new WebSocket();
		webSocketData1 = "Leave\n";
		webSocketData2 = "Join\n1\nHola";
	}
	
	@Test
	public void testDecode() {
		assertEquals(WebSocketData.decode(webSocketData1).encode(), "Leave\n");
		assertEquals(WebSocketData.decode(webSocketData2).encode(), "Join\n1\nHola");
	}
}
