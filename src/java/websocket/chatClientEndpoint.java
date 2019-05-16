/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 *
 * @author emlar
 */

@ClientEndpoint
public class chatClientEndpoint {
    Session session = null;
    
    public void chatClientEndpoint() throws URISyntaxException, DeploymentException, IOException{
        URI uri = new URI("//localhost:8080/senal_websocket/Websocket");
        this.session = ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
        System.out.println(session);
    }
    
    @OnOpen
    public void processOpen(Session session){
        this.session = session;
        System.out.println(session);
    }
    
    @OnMessage
    public void processMessage(String onmessage) throws IOException{
        System.out.println(onmessage);
        this.session.getBasicRemote().sendText(onmessage);
    }
    
}
