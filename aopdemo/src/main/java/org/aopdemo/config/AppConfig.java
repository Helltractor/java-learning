package org.aopdemo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类
 */

@Configuration
@ComponentScan(basePackageClasses = {org.aopdemo.test.IBuy.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {
}