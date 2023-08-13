package com.xyz.ws.conn;

import com.xyz.base.utils.LogUtils;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author ZKKzs
 **/
@ServerEndpoint("/api/ws/{id}")
@Component
public class IdSocket {
    private final Logger logger = LoggerFactory.getLogger(IdSocket.class);

    private String userId;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String userId) {
        logger.info(String.format("建立WebSocket连接, userId: %s, info: %s",userId, LogUtils.success()));
        this.userId = userId;
        System.out.println("建立WebSocket连接, userId: "+ userId);
    }


    @OnClose
    public void onClose() {
        System.out.println("断开连接，userId： " + this.userId);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            System.out.println(msg);
            session.getBasicRemote().sendText("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
