# Spring Boot

## @SpringBootApplication

`@SpringBootApplication` 是 Spring Boot 中一个常用的注解，通常用于主类上，以标识这是一个 Spring Boot 应用。它是一个组合注解，包含了多个其他注解，这使得 Spring Boot 应用的配置更加简单和便捷。下面是对这个注解及其包含的注解的详细解析。

### @SpringBootConfiguration

这个注解是 `@Configuration` 的特化版本，主要用于 Spring Boot 项目中，表明该类是一个配置类，可以用来定义 bean。

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {
}
```

### @EnableAutoConfiguration

这个注解启用 Spring Boot 的自动配置机制，根据项目中的依赖和配置自动配置 Spring 应用。

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
    // 排除某些自动配置类
    Class<?>[] exclude() default {};

    // 排除某些自动配置类的名称
    String[] excludeName() default {};
}
```

### @ComponentScan

这个注解用于组件扫描，扫描主类所在包及其子包中的组件。

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
    // 要扫描的基础包
    @AliasFor("basePackages")
    String[] value() default {};

    // 要扫描的基础包
    @AliasFor("value")
    String[] basePackages() default {};

    // 要扫描的基础包类
    Class<?>[] basePackageClasses() default {};

    // 指定用于生成bean名称的生成器
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    // 其他属性省略...
}
```

### @SpringBootApplication的详细代码

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
        type = FilterType.CUSTOM,
        classes = {TypeExcludeFilter.class}
    ), @Filter(
        type = FilterType.CUSTOM,
        classes = {AutoConfigurationExcludeFilter.class}
    )}
)
public @interface SpringBootApplication {
    // 指定要排除的自动配置类
    @AliasFor(annotation = EnableAutoConfiguration.class)
    Class<?>[] exclude() default {};

    // 指定要排除的自动配置类名称
    @AliasFor(annotation = EnableAutoConfiguration.class)
    String[] excludeName() default {};

    // 指定要扫描的基础包
    @AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

    // 指定要扫描的基础包类
    @AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};

    // 指定用于生成bean名称的生成器
    @AliasFor(annotation = ComponentScan.class, attribute = "nameGenerator")
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    // 指定配置类是否代理其bean方法
    @AliasFor(annotation = Configuration.class)
    boolean proxyBeanMethods() default true;
}
```

### 总结

* `@SpringBootApplication` 是一个组合注解，包含 `@SpringBootConfiguration`、`@EnableAutoConfiguration` 和 `@ComponentScan`。
* 通过这个注解，Spring Boot 应用能够自动扫描和配置项目中的组件，并根据依赖自动进行配置，从而减少了开发者的配置工作量。
* 该注解通过使用 `@AliasFor` 将属性代理给包含的注解，简化了配置。

使用 `@SpringBootApplication` 可以大大简化 Spring Boot 应用的启动和配置过程，开发者只需在主类上标注这个注解即可完成大部分配置工作。

## SpringApplication.run()

这段代码展示了Spring Boot应用的启动过程。`SpringApplication`类的`run`方法是Spring Boot启动的核心，它负责初始化、配置和运行Spring应用。下面详细解析这个方法的每一步，以帮助更好地理解Spring Boot应用的启动流程。

### `run` 方法解析

```java 17
public ConfigurableApplicationContext run(String... args) {
    // 1. 创建启动对象，用于记录启动时间和相关信息
    Startup startup = SpringApplication.Startup.create();
    
    // 2. 如果启用了关闭钩子，则注册关闭钩子
    if (this.registerShutdownHook) {
        shutdownHook.enableShutdownHookAddition();
    }

    // 3. 创建引导上下文，默认实现为空上下文，可能用于存储一些临时数据
    DefaultBootstrapContext bootstrapContext = this.createBootstrapContext();
    ConfigurableApplicationContext context = null;
    
    // 4. 配置headless属性
    this.configureHeadlessProperty();
    
    // 5. 获取启动监听器，并发布启动事件
    SpringApplicationRunListeners listeners = this.getRunListeners(args);
    listeners.starting(bootstrapContext, this.mainApplicationClass);

    Throwable ex;
    try {
        // 6. 解析应用程序参数
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        
        // 7. 准备环境，包含系统环境变量、应用配置等
        ConfigurableEnvironment environment = this.prepareEnvironment(listeners, bootstrapContext, applicationArguments);
        
        // 8. 打印Banner
        Banner printedBanner = this.printBanner(environment);
        
        // 9. 创建应用上下文
        context = this.createApplicationContext();
        context.setApplicationStartup(this.applicationStartup);
        
        // 10. 准备上下文，包括环境、监听器、参数等的注入
        this.prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
        
        // 11. 刷新上下文，初始化所有Spring容器中的bean
        this.refreshContext(context);
        
        // 12. 上下文刷新后的操作
        this.afterRefresh(context, applicationArguments);
        
        // 13. 启动对象记录启动完成的时间
        startup.started();
        
        // 14. 记录启动信息日志
        if (this.logStartupInfo) {
            (new StartupInfoLogger(this.mainApplicationClass)).logStarted(this.getApplicationLog(), startup);
        }

        // 15. 发布上下文启动完成事件
        listeners.started(context, startup.timeTakenToStarted());
        
        // 16. 调用所有应用程序运行器
        this.callRunners(context, applicationArguments);
    } catch (Throwable var10) {
        ex = var10;
        // 17. 处理运行时失败，捕获异常并处理
        throw this.handleRunFailure(context, ex, listeners);
    }

    try {
        // 18. 如果上下文正在运行，发布应用准备就绪事件
        if (context.isRunning()) {
            listeners.ready(context, startup.ready());
        }

        // 19. 返回应用上下文
        return context;
    } catch (Throwable var9) {
        ex = var9;
        // 20. 处理准备就绪阶段的失败
        throw this.handleRunFailure(context, ex, (SpringApplicationRunListeners)null);
    }
}
```

### 各步骤详细解析

1. **创建启动对象**：用来记录启动时间和相关信息，帮助分析启动性能。
2. **注册关闭钩子**：在JVM关闭时执行一些清理操作。
3. **创建引导上下文**：用于存储启动过程中需要的临时数据。
4. **配置headless属性**：配置headless模式，通常在没有显示设备的环境中运行时使用。
5. **获取启动监听器**：启动监听器用于监听Spring Boot应用的生命周期事件。
6. **解析应用程序参数**：解析传递给应用的命令行参数。
7. **准备环境**：准备Spring应用的运行环境，包括读取系统属性和配置文件。
8. **打印Banner**：在控制台打印Banner信息，通常是应用的欢迎信息。
9. **创建应用上下文**：Spring的IOC容器，管理应用的bean。
10. **准备上下文**：将环境、监听器、参数等注入到应用上下文中。
11. **刷新上下文**：初始化所有Spring容器中的bean，执行依赖注入。
12. **上下文刷新后的操作**：在上下文刷新后执行一些定制的操作。
13. **记录启动时间**：记录启动完成的时间，用于性能分析。
14. **记录启动信息日志**：将启动信息记录到日志中。
15. **发布上下文启动完成事件**：通知所有监听器上下文启动完成。
16. **调用应用程序运行器**：执行实现了`ApplicationRunner`或`CommandLineRunner`接口的bean。
17. **处理运行时失败**：捕获启动过程中的异常并处理。
18. **发布应用准备就绪事件**：通知所有监听器应用已准备就绪。
19. **返回应用上下文**：返回启动后的应用上下文。
20. **处理准备就绪阶段的失败**：捕获准备就绪过程中的异常并处理。

### 总结

通过这些步骤，Spring Boot在启动时完成了环境准备、上下文初始化、bean加载和依赖注入、监听器通知等一系列操作，确保应用能够正确启动和运行。如果启动过程中发生异常，则通过捕获和处理异常，确保应用能够尽可能优雅地应对启动失败的情况。

## Spring Boot 启动流程

1. **创建 `SpringApplication` 对象**
* 使用传入的主类（通常包含 `@SpringBootApplication` 注解的类）创建一个 `SpringApplication` 实例。

2. **设置默认属性**
* 设置一些默认属性，例如 banner 模式、是否添加关闭钩子等。

3. **准备 `SpringApplicationRunListeners`**
* 初始化并调用所有的 `SpringApplicationRunListeners`，监听应用的生命周期事件。

4. **准备环境**
* 创建并配置 `ConfigurableEnvironment`，包括读取系统属性、环境变量和配置文件等。
* 发布 `EnvironmentPreparedEvent` 事件。

5. **打印 Banner**
* 在控制台打印 Banner 信息（如果启用了 Banner）。

6. **创建应用上下文**
* 根据应用类型（例如 `ServletWebServerApplicationContext` 或 `ReactiveWebServerApplicationContext`）创建合适的应用上下文。

7. **准备上下文**
* 准备并配置应用上下文，包括设置环境、设置应用监听器、加载配置类等。
* 将 `ApplicationArguments` 和 `BootstrapContext` 注入到上下文中。
* 发布 `ApplicationPreparedEvent` 事件。

8. **刷新上下文**
* 刷新应用上下文，初始化所有 Spring 容器中的 bean，执行依赖注入。
* 注册所有的 `CommandLineRunner` 和 `ApplicationRunner`。

9. **后处理**
* 在上下文刷新后执行一些定制的操作，例如启动嵌入式服务器等。
* 发布 `ApplicationStartedEvent` 事件。

10. **调用运行器**
* 调用所有实现了 `CommandLineRunner` 和 `ApplicationRunner` 接口的 bean。

11. **准备就绪**
* 发布 `ApplicationReadyEvent` 事件，表示应用已准备就绪。

12. **处理启动失败**
* 如果启动过程中出现异常，捕获并处理异常，发布 `ApplicationFailedEvent` 事件。

## Reference

- [Java面试题：Spring中的循环依赖，给程序员带来的心理阴影](https://www.cnblogs.com/marsitman/p/18195525)
- [《Spring Boot精髓：启动流程源码分析》](https://www.cnblogs.com/java-chen-hao/p/11829056.html)
- [SpringBoot3.x中spring.factories功能被移除的解决方案](https://www.cnblogs.com/throwable/p/16950353.html)
- ~~[《Spring Boot自动装配原理，这一篇就够了!》](https://mp.weixin.qq.com/s/f6oED1hbiWat_0HOwxgfnA)~~