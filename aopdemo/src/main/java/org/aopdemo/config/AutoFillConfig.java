package org.aopdemo.config;

import org.aopdemo.AutoFillTest;
import org.aopdemo.service.IBuy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类
 * 配置 Spring Bean 扫描的基础路径和代理方式
 */

@Configuration

// 配置扫描的基础路径
@ComponentScan(basePackageClasses = {IBuy.class, AutoFillTest.class})

// 配置代理方式
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AutoFillConfig {
}