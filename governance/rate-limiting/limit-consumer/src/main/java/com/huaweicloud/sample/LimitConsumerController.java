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
public class LimitConsumerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(LimitConsumerController.class);

  @Autowired
  private RestTemplate restTemplate;

  @RequestMapping("/period/quickly-limit")
  public String quicklyLimit() {
    try {
      return restTemplate.getForObject("http://limit-provider/period/quickly-limit", String.class);
    } catch (Exception e) {
      LOGGER.warn("quickly-limit failed: " + e.getMessage());
      if (e.getMessage().contains("rate limited")) {
        return "rate limited!";
      }
      throw e;
    }
  }

  @RequestMapping("/period/reduce-limit")
  public String reduceLimit() {
    try {
      return restTemplate.getForObject("http://limit-provider/period/reduce-limit", String.class);
    } catch (Exception e) {
      LOGGER.warn("reduce-limit failed: " + e.getMessage());
      if (e.getMessage().contains("rate limited")) {
        return "rate limited!";
      }
      throw e;
    }
  }

  @RequestMapping("/duration/not-limit")
  public String timeoutNotLimit() {
    try {
      return restTemplate.getForObject("http://limit-provider/duration/not-limit", String.class);
    } catch (Exception e) {
      LOGGER.warn("timeout not-limit failed: " + e.getMessage());
      if (e.getMessage().contains("rate limited")) {
        return "rate limited!";
      }
      throw e;
    }
  }

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
