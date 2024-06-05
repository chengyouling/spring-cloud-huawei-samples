# 项目说明  [English document](README.md)

这个项目提供了 Spring Boot 微服务开发到集成 Spring Cloud 实现注册发现能力的 Spring Cloud Huawei 简单例子，例子包括：

* provider
  使用 Spring Cloud 开发一个 REST 接口。

* consumer
  使用 Spring Cloud 开发一个 REST 接口， 接口实现分別通过 RestTemplate 调用 provider 的接口。

* gateway
  使用 Spring Cloud Gateway 开发一个网关， 网关将所有请求转发到 consumer。

## Spring Boot微服务开发
  当前demo默认是Spring Boot开发的微服务。

* 编译
   
      mvn clean package

* 启动 provider

  进入目录 ${Project}/provider/target/

        java -jar basic-provider-1.0-SNAPSHOT.jar
* 启动 consumer

  进入目录 ${Project}/consumer/target/

        java -jar basic-consumer-1.0-SNAPSHOT.jar
* 启动 gateway

        java -jar basic-gateway-1.0-SNAPSHOT.jar

* 测试

启动3个微服务后， 然后通过界面访问： http://localhost:9090/consumer/sayHelloBoot?name=World ,测试返回结果 Hello World

## 集成 Spring Cloud Huawei 微服务开发

* 前提条件
  [准备CSE(Servicecomb)运行环境](../CSE-ENV_CN.md)

* 微服务改造

  父pom打开spring-cloud-huawei依赖管理：

       <spring-cloud-huawei.version>1.11.7-2021.0.x</spring-cloud-huawei.version>      
       
      <dependency>
        <groupId>com.huaweicloud</groupId>
        <artifactId>spring-cloud-huawei-dependencies</artifactId>
        <version>${spring-cloud-huawei.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

  provider配置、依赖管理：

    ProviderApplication类打开注册发现注解：
    
      @EnableDiscoveryClient
  
    pom中打开spring-cloud-starter-huawei-service-engine依赖

      <dependency>
        <groupId>com.huaweicloud</groupId>
        <artifactId>spring-cloud-starter-huawei-service-engine</artifactId>
        <exclusions>
          <exclusion>
            <groupId>com.huaweicloud</groupId>
            <artifactId>spring-cloud-starter-huawei-swagger</artifactId>
          </exclusion>
        </exclusions>
      </dependency>

  consumer配置、依赖管理：

    ConsumerApplication类打开注册发现注解：
  
        @EnableDiscoveryClient
  
    pom中打开spring-cloud-starter-huawei-service-engine依赖
  
        <dependency>
          <groupId>com.huaweicloud</groupId>
          <artifactId>spring-cloud-starter-huawei-service-engine</artifactId>
          <exclusions>
            <exclusion>
              <groupId>com.huaweicloud</groupId>
              <artifactId>spring-cloud-starter-huawei-swagger</artifactId>
            </exclusion>
          </exclusions>
        </dependency>
   
    如果需要测试feign调用，打开相关配置和依赖
       
        FeignConsumerService类中打开@FeignClient(value = "basic-provider")
        
        ConsumerApplication类中打开@EnableFeignClients注解
        
        打开依赖：
        
        <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

  gateway配置、依赖管理：

    GatewayApplication类打开注册发现注解：
  
          @EnableDiscoveryClient
  
    pom中打开spring-cloud-starter-huawei-service-engine-gateway依赖
  
          <dependency>
            <groupId>com.huaweicloud</groupId>
            <artifactId>spring-cloud-starter-huawei-service-engine-gateway</artifactId>
          </dependency>

    application.yaml中打开基于负载均衡的路由，注释基于服务地址端口的路由：

          # 基于服务地址端口路由
          uri: http://127.0.0.1:9092
          # 基于负载均衡的路由
          uri: lb://basic-consumer

* 编译

        mvn clean package
* 启动 provider

  进入目录 ${Project}/provider/target/

        java -jar basic-provider-1.0-SNAPSHOT.jar
* 启动 consumer

  进入目录 ${Project}/consumer/target/

        java -jar basic-consumer-1.0-SNAPSHOT.jar
* 启动 gateway

  进入目录 ${Project}/gateway/target/

        java -jar basic-gateway-1.0-SNAPSHOT.jar

* 测试

启动3个微服务后， 然后通过界面访问： http://localhost:9090/consumer/sayHello?name=World ,测试返回结果 Hello World
