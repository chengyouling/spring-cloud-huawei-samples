package com.huaweicloud.samples.consumer;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.huaweicloud.samples.websocket.WebSocketClient;

@RestController
public class ConsumerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerController.class);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private FeignConsumerService feignConsumerService;

  @Value("${websocket_server_address:127.0.0.1}")
  private String webSocketServerAddress;

  @Value("${websocket_server_port:9094}")
  private String webSocketServerPort;

  //if use nacos, remove this field
//  private ConfigListen configListen;
//
//  @Autowired
//  public ConsumerController(ConfigListen configListen) {
//    this.configListen = configListen;
//  }

  // consumer service which delegate the implementation to provider service.
  @GetMapping("/sayHello")
  public String sayHello(@RequestParam("name") String name) {
    return restTemplate.getForObject("http://basic-provider/sayHello?name={1}", String.class, name);
  }

  @GetMapping("/sayHelloFeign")
  public String sayHelloFeign(@RequestParam("name") String name) {
    return feignConsumerService.sayHelloFeign(name);
  }

  @GetMapping("/testConfig")
  public String testConfig() {
    return restTemplate.getForObject("http://basic-provider/testConfig", String.class);
  }

  @GetMapping("/testWebSocket")
  public String testWebSocket() {
    try {
      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      Session session = container.connectToServer(WebSocketClient.class,
          URI.create("ws://" + webSocketServerAddress + ":" + webSocketServerPort + "/webSocket"));
      session.getBasicRemote().sendText("Hello, WebSocket");
      return "success";
    } catch (Exception e) {
      LOGGER.error("websocket创建连接失败, address:port= {}:{}", webSocketServerAddress, webSocketServerPort, e);
      return "failed";
    }
  }
}
