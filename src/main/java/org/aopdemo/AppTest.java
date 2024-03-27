package org.aopdemo;

import org.aopdemo.config.AppConfig;
import org.aopdemo.test1.Boy;
import org.aopdemo.test1.Girl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试类
 */

public class AppTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        // ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext("aopdemo.xml");
        Boy boy = context.getBean("boy", Boy.class);
        Girl girl = (Girl) context.getBean("girl");
        // boy.buy();
        // girl.buy();
        String boyBought = boy.buyPrice(35);
        String girlBought = girl.buyPrice(99.8);

        System.out.println("男孩买到了：" + boyBought);
        System.out.println("女孩买到了：" + girlBought);
    }
}
