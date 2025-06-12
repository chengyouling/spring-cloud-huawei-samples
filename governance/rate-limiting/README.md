# Description
This project providers sample to show working with rate-limiting Deploy. 

* limit-provider
A Microserivce using Spring Cloud with a REST interface.

* not-limit-consumer
A Microserivce using Spring Cloud with a REST interface. Consumer calls limit-provider with RestTemplate.

* limit-consumer
A Microserivce using Spring Cloud with a REST interface. Consumer calls limit-provider with RestTemplate.

# Build and Run

* Prerequisites
[Setup CSE environment](../CSE-ENV.md)

[Setup CSE(Nacos) environment](../NACOS-ENV.md)

* Build
  CSE(Servicecomb)

        mvn clean package -Pcse
  CSE(Nacos)

        mvn clean package -Pnacos

* Run limit-provider

  In ${Project}/limit-provider/target/
  CSE(Servicecomb)

        java -jar -Dspring.profiles.active=cse limit-provider-1.0-SNAPSHOT.jar
  CSE(Nacos)

        java -jar -Dspring.profiles.active=nacos limit-provider-1.0-SNAPSHOT.jar
  
* Run not-limit-consumer

  In ${Project}/not-limit-consumer/target/
  CSE(Servicecomb)

        java -jar -Dspring.profiles.active=cse not-limit-consumer-1.0-SNAPSHOT.jar
  CSE(Nacos)

        java -jar -Dspring.profiles.active=nacos not-limit-consumer-1.0-SNAPSHOT.jar
  
* Run limit-consumer

  In ${Project}/limit-consumer/target/
  CSE(Servicecomb)

        java -jar -Dspring.profiles.active=cse limit-consumer-1.0-SNAPSHOT.jar
  CSE(Nacos)

        java -jar -Dspring.profiles.active=nacos limit-consumer-1.0-SNAPSHOT.jar

* Testing

1、set the value of limitRefreshPeriod to a larger value to quickly trigger traffic limiting, currently configuration file set 10 second.

Open in browser： http://localhost:8086/period/quickly-limit

Traffic limiting is triggered if more than two requests are sent within 10 seconds.

2、set the value of limitRefreshPeriod to a small value to reduce or not trigger limiting, currently configuration file set 10 milliseconds.

Open in browser： http://localhost:8086/period/reduce-limit

If no more than two requests are sent within 10 milliseconds, traffic limiting will not be triggered.

3、when the sum of limitRefreshPeriod and interface consumption time is less than timeoutDuration, traffic limiting is not triggered.

Open in browser： http://localhost:8086/timeout/not-limit 

Traffic limiting cannot be triggered for fast requests.

4、trigger traffic limiting based on the client service name.

Open in browser： http://localhost:8086/serviceName/limit 

Traffic limiting is triggered if more than two requests are sent within 10 seconds.

Open in browser： http://localhost:8088/serviceName/limit

Traffic limiting is not triggered for more than two requests within 10 seconds.

# 项目说明

这个项目提供了 Spring Cloud Huawei 限流治理的例子。

* limit-provider
使用 Spring Cloud 开发一个 REST 接口。

* not-limit-consumer
使用 Spring Cloud 开发一个 REST 接口， 接口实现通过 RestTemplate 调用 limit-provider 的接口。

* limit-consumer
使用 Spring Cloud 开发一个 REST 接口， 接口实现通过 RestTemplate 调用 limit-provider 的接口。

## 使用

* 前提条件
[准备CSE运行环境](../CSE-ENV_CN.md)

[准备CSE(Nacos)运行环境](../NACOS-ENV_CN.md)

* 编译
  CSE(Servicecomb)

        mvn clean package -Pcse
  CSE(Nacos)

        mvn clean package -Pnacos

* 启动 limit-provider

  进入目录 ${Project}/limit-provider/target/
  CSE(Servicecomb)

        java -jar -Dspring.profiles.active=cse limit-provider-1.0-SNAPSHOT.jar
  CSE(Nacos)

        java -jar -Dspring.profiles.active=nacos limit-provider-1.0-SNAPSHOT.jar

* 启动 not-limit-consumer

  进入目录 ${Project}/not-limit-consumer/target/
  CSE(Servicecomb)

        java -jar -Dspring.profiles.active=cse not-limit-consumer-1.0-SNAPSHOT.jar
  CSE(Nacos)

        java -jar -Dspring.profiles.active=nacos not-limit-consumer-1.0-SNAPSHOT.jar
         
* 启动 limit-consumer

  进入目录 ${Project}/limit-consumer/target/
  CSE(Servicecomb)

        java -jar -Dspring.profiles.active=cse limit-consumer-1.0-SNAPSHOT.jar
  CSE(Nacos)

        java -jar -Dspring.profiles.active=nacos limit-consumer-1.0-SNAPSHOT.jar

* 测试

1、将limitRefreshPeriod设置大些来快速触发限流

界面访问：http://localhost:8086/period/quickly-limit  10秒内超过2次请求则触发限流

2、将limitRefreshPeriod设置小些来降低或不触发限流

界面访问：http://localhost:8086/period/reduce-limit  10毫秒内不超过2个请求将不会触发限流

3、当limitRefreshPeriod加上接口耗时小于timeoutDuration时将不会触发限流

界面访问：http://localhost:8086/timeout/not-limit  快速请求将不会触发限流

4、根据客户端服务名触发限流

界面访问：http://localhost:8086/serviceName/limit 10秒内超过2次请求则触发限流

界面访问：http://localhost:8088/serviceName/limit 10秒内超过2次请求不会触发限流