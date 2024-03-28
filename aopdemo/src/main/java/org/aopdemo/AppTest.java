package org.aopdemo;

import org.aopdemo.config.AppConfig;
import org.aopdemo.test.Boy;
import org.aopdemo.test.Girl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * 测试类
 */
public class AppTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Boy boy = context.getBean("boy", Boy.class);
        Girl girl = (Girl) context.getBean("girl");

        boy.buy();
        girl.buy();

        String boyBought = boy.buyPrice(35);
        String girlBought = girl.buyPrice(99.8);

        System.out.println("男孩买到了：" + boyBought);
        System.out.println("女孩买到了：" + girlBought);
    }
}
