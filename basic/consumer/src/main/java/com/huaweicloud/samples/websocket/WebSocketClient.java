package com.huaweicloud.samples.websocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientEndpoint
public class WebSocketClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClient.class);

  @OnOpen
  public void onOpen(Session session) {
    LOGGER.info("连接成功");
  }
  @OnMessage
  public void onMessage(String message) {
    LOGGER.info("客户端收到消息：" + message);
  }
  @OnClose
  public void onClose() {
    LOGGER.info("连接关闭");
  }
}
