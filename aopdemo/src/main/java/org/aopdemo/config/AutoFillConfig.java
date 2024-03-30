package org.aopdemo.config;

import org.aopdemo.AutoFillTest;
import org.aopdemo.service.IBuy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类
 */

@Configuration
@ComponentScan(basePackageClasses = {IBuy.class, AutoFillTest.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AutoFillConfig {
}