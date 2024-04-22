# Error starting ApplicationContext

>To display the condition evaluation report re-run your application with 'debug' enabled.

## Case 1 : Bean not found

就是 Bean 没有找到，原因是没有扫描到 mapper 包下的 mapper 接口，导致无法注入 mapper 接口。或者没有进行 mybatis 的配置。

### Description

```
Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2024-04-22T16:44:51.265+08:00 ERROR 5456 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

Field userMapper in org.springcache.controller.UserController required a bean of type 'org.springcache.mapper.UserMapper' that could not be found.

The injection point has the following annotations:
	- @org.springframework.beans.factory.annotation.Autowired(required=true)


Action:

Consider defining a bean of type 'org.springcache.mapper.UserMapper' in your configuration.
```

### Solution

#### Step 1

在 springboot 的配置文件添加 mybatis 的配置，如 application.yml, application.properties等。

```yml
mybatis:
  #mapper配置文件
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: org.xxx.entity
  configuration:
    #开启驼峰命名
    map-underscore-to-camel-case: true
```

#### Step 2

1. 在启动类上加上@MapperScan或者@ComponentScan annotation，手动指定application类要扫描哪些包下的注解。

```java
@SpringBootApplication
@MapperScan("org.xxx.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

2. 或在mapper interface 加上@Mapper annotation。
```java
@Mapper
public interface UserMapper {
    List<User> selectAll();
}
```

## Reference

1. [springboot 工程启动报错之Consider defining a bean of type ‘XXX’ in your configuration.](https://www.cnblogs.com/nananana/p/9333917.html)

## Case 2 : Invalid value type for attribute 'factoryBeanObjectType'
 
factory bean 的对象类型属性 'factoryBeanObjectType' 的值无效。这个项目中使用的是 spring 和 mybatis-spring, 但是 mybatis-spring 的版本过低，只能通过降低 spring 版本解决问题。

### Description

```
Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2024-04-22T16:57:06.515+08:00 ERROR 3124 --- [           main] o.s.boot.SpringApplication               : Application run failed

java.lang.IllegalArgumentException: Invalid value type for attribute 'factoryBeanObjectType': java.lang.String
	at org.springframework.beans.factory.support.FactoryBeanRegistrySupport.getTypeForFactoryBeanFromAttributes(FactoryBeanRegistrySupport.java:86) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.getTypeForFactoryBean(AbstractAutowireCapableBeanFactory.java:838) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractBeanFactory.isTypeMatch(AbstractBeanFactory.java:620) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doGetBeanNamesForType(DefaultListableBeanFactory.java:573) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanNamesForType(DefaultListableBeanFactory.java:532) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:138) ~[spring-context-6.1.1.jar:6.1.1]
	at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:775) ~[spring-context-6.1.1.jar:6.1.1]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:597) ~[spring-context-6.1.1.jar:6.1.1]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:753) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:455) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:323) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1342) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1331) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springcache.DemoApplication.main(DemoApplication.java:15) ~[classes/:na]
```

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<groupId>com.demo</groupId>
<artifactId>demo</artifactId>
<version>1.0-SNAPSHOT</version>
<packaging>pom</packaging>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.2.0</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Solution
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <!--modify-->
    <version>2.7.3</version>    
</parent>

<groupId>com.demo</groupId>
<artifactId>demo</artifactId>
<version>1.0-SNAPSHOT</version>
<packaging>pom</packaging>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.2.0</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Reference
1. [mybatis-spring](http://doc.vrd.net.cn/mybatis/spring/zh/index.html)