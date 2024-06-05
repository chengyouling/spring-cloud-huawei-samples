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

  // Feign请求注解，测试feign请求时打开
//  @Autowired
//  private FeignConsumerService feignConsumerService;

  // 基于服务发现rest接口测试
  @GetMapping("/sayHello")
  public String sayHello(@RequestParam("name") String name) {
    return restTemplate.getForObject("http://basic-provider/sayHello?name={1}", String.class, name);
  }

  // spring-boot单独开发时rest接口测试
  @GetMapping("/sayHelloBoot")
  public String sayHelloBoot(@RequestParam("name") String name) {
    return restTemplate.getForObject("http://127.0.0.1:9094/sayHello?name={1}", String.class, name);
  }

  // Feign请求注解，测试feign请求时打开
//  @GetMapping("/sayHelloFeign")
//  public String sayHelloFeign(@RequestParam("name") String name) {
//    return feignConsumerService.sayHelloFeign(name);
//  }
}
