package org.aopdemo.entity;

import org.aopdemo.service.IBuy;
import org.springframework.stereotype.Component;

/**
 * 实现类
 * 实现接口
 */

@Component
public class Girl implements IBuy {
    @Override
    public String buy() {
        System.out.println("女孩买了一件漂亮的衣服");
        return "衣服";
    }

    @Override
    public final String buyPrice(double price) {
        System.out.printf("女孩花了%s元买了一件漂亮的衣服%n", price);
        return "衣服";
    }
}
