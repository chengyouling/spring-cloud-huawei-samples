package com.huaweicloud.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @Author GuoYl123
 * @Date 2019/10/22
 **/
@RestController
public class ProviderController {

  @Value("${server.port}")
  private Integer port;

  @Value("${spring.cloud.servicecomb.service.version}")
  private String version;

  @RequestMapping("/provider")
  public String sayHello(@RequestParam("id") String id) {
    return "beta: provider ---> " + id + " port -->" + port + " ,version--->" + version;
  }

  @RequestMapping("/canaryFallback")
  public String canaryFallback() {
    return "beta: provider test fallback method, port -->" + port + " ,version--->" + version;
  }

  @RequestMapping("/canaryHeaderIsolation")
  public String canaryHeaderIsolation(HttpServletResponse response) {
    response.addHeader("X-HTTP-STATUS-CODE", "502");
    return "beta: provider isolation method, port -->" + port + " ,version--->" + version;
  }

  @RequestMapping("/canaryHeaderCircuit")
  public String canaryHeaderCircuit(HttpServletResponse response) {
    response.addHeader("X-HTTP-STATUS-CODE", "502");
    return "beta: provider circuit method, port -->" + port + " ,version--->" + version;
  }

  @RequestMapping("/canaryHeaderRetry")
  public String canaryHeaderRetry() {
    return "provider retry method, port -->" + port + " ,version--->" + version;
  }
}
