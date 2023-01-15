package org.jboss.additional.testsuite.jdkall.present.web.websockets;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#28.0.0"})
@ServerEndpoint("/hello")
public class HelloWorldEndpoint {
    
    @OnMessage
    public String hello(String message) {
        System.out.println("Received : "+ message);
        return message;
    }
    @OnOpen
    public void myOnOpen(Session session) {
        System.out.println("WebSocket opened: " + session.getId());
    }
    @OnClose
    public void myOnClose(CloseReason reason) {
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }
}
 
