package com.huaweicloud.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author chengyouling
 * @Date 2025/6/3
 **/
@RestController
public class NotLimitConsumerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(NotLimitConsumerController.class);

  @Autowired
  private RestTemplate restTemplate;

  @RequestMapping("/serviceName/limit")
  public String serviceNameLimit() {
    try {
      return restTemplate.getForObject("http://limit-provider/serviceName/limit", String.class);
    } catch (Exception e) {
      LOGGER.warn("serviceName limit failed: " + e.getMessage());
      if (e.getMessage().contains("rate limited")) {
        return "rate limited!";
      }
      throw e;
    }
  }
}
