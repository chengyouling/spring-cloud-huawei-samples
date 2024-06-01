# 项目说明 

这个项目提供了 Spring Cloud Huawei 的CSE(Servicecomb)与CSE(Nacos)间切换简单例子，例子包括：

* provider
  使用 Spring Cloud 开发一个 REST 接口。

* consumer
  使用 Spring Cloud 开发一个 REST 接口， 接口实现分別通过 RestTemplate和Feign 调用 provider 的接口。

* gateway
  使用 Spring Cloud Gateway 开发一个网关， 网关将所有请求转发到 consumer。

## 使用
  demo中提供了CSE(Servicecomb)与CSE(Nacos)注册、配置中心切换的改造实践，根据场景及业务需要平滑切换使用CSE(Servicecomb)或CSE(Nacos)注册、配置中心。

* 前提条件

  [准备CSE(Servicecomb)运行环境](../CSE-ENV_CN.md)

  [准备CSE(Nacos)运行环境](../NACOS-ENV_CN.md)

* 项目改造调整

  CSE(Servicecomb)与CSE(Nacos)间切换需调整pom依赖，根据spring boot、spring cloud版本三方依赖要求，调整业务三方件依赖；
  demo中以1.10.1-2020.0.x/1.10.1-2021.0.x版本使用CSE(Servicecomb)升级到1.11.7-2021.0.x版本使用CSE(Nacos)为例，介绍主要改造内容。
  
  1、父pom调整依赖管理

      <properties>
          <spring-boot.version>2.5.12</spring-boot.version>
          <spring-cloud.version>2020.0.5</spring-cloud.version>
          <spring-cloud-huawei.version>1.10.1-2020.0.x</spring-cloud-huawei.version>
      </properties>
      或
      <properties>
          <spring-boot.version>2.6.7</spring-boot.version>
          <spring-cloud.version>2021.0.3</spring-cloud.version>
          <spring-cloud-huawei.version>1.10.1-2021.0.x</spring-cloud-huawei.version>
      </properties>

      调整为：
      <properties>
          <spring-boot.version>2.7.18</spring-boot.version>
          <spring-cloud.version>2021.0.9</spring-cloud.version>
          <spring-cloud-huawei.version>1.11.7-2021.0.x</spring-cloud-huawei.version>
      </properties>

  2、微服务provider、consumer中pom依赖调整

      <dependency>
          <groupId>com.huaweicloud</groupId>
          <artifactId>spring-cloud-starter-huawei-service-engine</artifactId>
      </dependency>
      调整为：
      <dependency>
          <groupId>com.huaweicloud</groupId>
          <artifactId>spring-cloud-starter-huawei-nacos</artifactId>
      </dependency>

  3、网关gateway中pom依赖调整

      <dependency>
          <groupId>com.huaweicloud</groupId>
          <artifactId>spring-cloud-starter-huawei-service-engine-gateway</artifactId>
      </dependency>
      调整为：
      <dependency>
          <groupId>com.huaweicloud</groupId>
          <artifactId>spring-cloud-starter-huawei-nacos-gateway</artifactId>
      </dependency>
  4、配置文件调整

    Servicecomb注册中心配置
      
      spring:
        cloud:
          servicecomb:
            # 注册发现相关配置
            discovery:
              # 注册中心地址，本示例使用ServiceStage环境变量。建议保留这种配置方式，
              # 部署的时候，不用手工修改地址。
              address: ${PAAS_CSE_SC_ENDPOINT:http://127.0.0.1:30100}
              # 微服务向CSE发送心跳间隔时间，单位秒
              healthCheckInterval: 10
              # 拉取实例的轮询时间，单位毫秒
              pollInterval: 15000
              # 优雅停机设置。优雅停机后，先从注册中心注销自己。这个时间表示注销自己后等待的时间，这个时间后才退出。
              waitTimeForShutDownInMillis: 15000
              # 环境名称。只有环境名称相同的微服务之间才可以相互发现。
              # 可以取值 development, testing, acceptance, production
              environment: production
              # 应用名称。默认情况下只有应用名称相同的微服务之间才可以相互发现。
              appName: ${CAS_APPLICATION_NAME:servicecomb-2nacos}
              # 微服务版本号，本示例使用ServiceStage环境变量。建议保留这种配置方式，
              # 部署的时候，不用手工修改版本号，防止契约注册失败。
              version: ${CAS_INSTANCE_VERSION:0.0.1}
              # 微服务名称，和spring.application.name保持一致。
              serviceName: ${spring.application.name}
            config:
              # 配置中心地址，本示例使用ServiceStage环境变量。建议保留这种配置方式，
              # 部署的时候，不用手工修改地址。
              serverAddr: ${PAAS_CSE_CC_ENDPOINT:http://127.0.0.1:30110}
              serverType: kie
              # 自定义配置，使用文本的key/value配置项作为yaml格式配置
              fileSource: governance.yaml,application.yaml

    调整为Nacos注册中心配置：

      spring:
        cloud:
          nacos:
            discovery:
              enabled: true
              server-addr: http://127.0.0.1:8848
              service: ${spring.application.name}
            config:
              enabled: true
              server-addr: http://127.0.0.1:8848
              file-extension: yaml
              shared-configs[0]:
                data-id: provider.yaml
                group: DEFAULT_GROUP
                refresh: true
* 编译
  CSE(Servicecomb)场景

      1、父pom中打开‘servicecomb registry 2020.0.x依赖管理’或者‘servicecomb registry 2021.0.x依赖管理’，注释掉‘nacos registry 
      依赖管理’；
      2、provider、consumer、gateway子项目打开spring-cloud-starter-huawei-service-engine或spring-cloud-starter-huawei-service
      -engine-gateway依赖，注释掉spring-cloud-starter-huawei-nacos或spring-cloud-starter-huawei-nacos-gateway依赖；
      3、bootstrap.yml中打开‘Servicecomb 注册配置中心’相关配置，注释‘Nacos 注册配置中心’相关配置；
      4、执行mvn clean install进行正常打包

  CSE(Nacos)场景

      1、父pom中注释掉‘servicecomb registry 2020.0.x依赖管理’和‘servicecomb registry 2021.0.x依赖管理’，打开‘nacos registry 
      依赖管理’；
      2、provider、consumer、gateway子项目注释spring-cloud-starter-huawei-service-engine或spring-cloud-starter-huawei-service
      -engine-gateway依赖，打开spring-cloud-starter-huawei-nacos或spring-cloud-starter-huawei-nacos-gateway依赖；
      3、bootstrap.yml中注释‘Servicecomb 注册配置中心’相关配置，打开‘Nacos 注册配置中心’相关配置；
      4、执行mvn clean install进行正常打包
* 启动 provider
  
    进入目录 ${Project}/provider/target/

      java -jar migrator-provider-1.0-SNAPSHOT.jar
* 启动 consumer
  
    进入目录 ${Project}/consumer/target/

      java -jar migrator-consumer-1.0-SNAPSHOT.jar
* 启动 gateway
  
    进入目录 ${Project}/gateway/target/

      java -jar migrator-gateway-1.0-SNAPSHOT.jar

* 测试

      启动3个微服务后， 然后通过界面访问： http://localhost:9090/consumer/sayHello?name=World 或者 
      http://localhost:9090/consumer/sayHelloFeign?name=World 正常返回World结果。