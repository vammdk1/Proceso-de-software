package es.deusto.spq.server.websockets;

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@WebServlet(urlPatterns="/websocket")
public class PictochatntWebSocketServlet extends WebSocketServlet{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -2517866131071314759L;

	@Override
    public void configure(WebSocketServletFactory factory) {
         
//		factory.getPolicy().setMaxTextMessageSize(Integer.MAX_VALUE);
//		factory.getPolicy().setMaxTextMessageBufferSize(Integer.MAX_VALUE);
//		factory.getPolicy().setMaxBinaryMessageBufferSize(Integer.MAX_VALUE);
//		factory.getPolicy().setMaxBinaryMessageSize(Integer.MAX_VALUE);
		factory.register(PictochatntWebSocketServer.class);
         
    }
 
}