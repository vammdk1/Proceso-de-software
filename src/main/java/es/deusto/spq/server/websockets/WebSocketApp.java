package es.deusto.spq.server.websockets;
 
import java.net.URI;
 
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import es.deusto.spq.client.PictochatntWebSocketClient;
 
public class WebSocketApp {
 
    public static void main(String[] args) {
        String dest = "ws://localhost:8080/websocket?token=aaa&room=bbb&password=ccc";
        WebSocketClient client = new WebSocketClient();
        try {
            PictochatntWebSocketClient socket = new PictochatntWebSocketClient();
            client.start();
            URI echoUri = new URI(dest);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            socket.getLatch().await();
            socket.sendMessage("echo");
            socket.sendMessage("test");
            Thread.sleep(1000);
 
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}