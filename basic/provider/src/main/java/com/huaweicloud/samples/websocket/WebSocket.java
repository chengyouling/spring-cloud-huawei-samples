package com.huaweicloud.samples.websocket;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint(value = "/webSocket")
public class WebSocket {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocket.class);

  private Session session;

  private static CopyOnWriteArraySet<WebSocket> webSocketSet=new CopyOnWriteArraySet<>();

  public WebSocket() {
    Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
      sendMessage("当前时间：" + System.currentTimeMillis());
    }, 10000, 10000, TimeUnit.MILLISECONDS);
  }

  @OnOpen
  public void opOpen(Session session){
    this.session=session;
    webSocketSet.add(this);
    LOGGER.info("websocket有新的连接,当前连接总数:{}", webSocketSet.size());
  }

  @OnClose
  public void onClose(){
    webSocketSet.remove(this);
    LOGGER.info("websocket连接断开,剩余连接总数:{}", webSocketSet.size());
  }

  @OnMessage
  public  void onMessage(String message){
    LOGGER.info("websocket,收到客户端发来的消息:{}", message);
  }

  public void sendMessage(String message){
    for (WebSocket webSocket : webSocketSet){
      LOGGER.info("websocket广播消息,message={}", message);
      try{
        webSocket.session.getBasicRemote().sendText(message);
      } catch (Exception e){
        LOGGER.error("websocket消息,广播消息,message={}", message, e);
      }
    }
  }
}
