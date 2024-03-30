package org.aopdemo.entity;

import org.aopdemo.service.IBuy;
import org.springframework.stereotype.Component;

/**
 * 实现类
 * 实现接口
 */

@Component
public class Boy implements IBuy {
    @Override
    public String buy() {
        System.out.println("男孩买了一个游戏机");
        return "游戏机";
    }

    @Override
    public String buyPrice(double price) {
        System.out.println(String.format("男孩花了%s元买了一个游戏机", price));
        return "游戏机";
    }
}