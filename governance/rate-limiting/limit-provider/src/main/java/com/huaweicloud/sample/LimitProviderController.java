package com.huaweicloud.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author chengyouling
 * @Date 2025/6/3
 **/
@RestController
public class LimitProviderController {
  @RequestMapping("/period/quickly-limit")
  public String quicklyLimit() throws InterruptedException {
    Thread.sleep(500);
    return "OK";
  }

  @RequestMapping("/period/reduce-limit")
  public String reduceLimit() throws InterruptedException {
    Thread.sleep(500);
    return "OK";
  }

  @RequestMapping("/duration/not-limit")
  public String timeoutNotLimit() throws InterruptedException {
    Thread.sleep(500);
    return "OK";
  }

  @RequestMapping("/serviceName/limit")
  public String serviceNameLimit() throws InterruptedException {
    Thread.sleep(500);
    return "OK";
  }
}
