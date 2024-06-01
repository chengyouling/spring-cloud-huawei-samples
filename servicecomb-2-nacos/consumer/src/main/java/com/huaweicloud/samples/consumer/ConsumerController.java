package com.huaweicloud.samples.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private FeignConsumerService feignConsumerService;

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
    return restTemplate.getForObject("http://migrator-provider/sayHello?name={1}", String.class, name);
  }

  @GetMapping("/sayHelloFeign")
  public String sayHelloFeign(@RequestParam("name") String name) {
    return feignConsumerService.sayHelloFeign(name);
  }

  @GetMapping("/testConfig")
  public String testConfig() {
    return restTemplate.getForObject("http://migrator-provider/testConfig", String.class);
  }
}
