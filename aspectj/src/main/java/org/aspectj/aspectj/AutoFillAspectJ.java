package org.aspectj.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 切面类
 * 修改切点周围信息
 */

@Aspect
@Component
public class AutoFillAspectJ {

    @Pointcut("execution(* org.aspectj.service.IBuy.buy(..))")
    public void aopPointCut() {

    }

    @Before("aopPointCut()")
    // @Before("execution(* org.aopdemo.service.IBuy.buy(..)) && within(org.aopdemo.entity.*) && bean(girl)")
    public void haha() {
        System.out.println("男孩女孩都买了自己喜欢的东西");
    }

    @After("aopPointCut()")
    public void hehe() {
        System.out.println("After ...");
    }

    @AfterReturning("aopPointCut()")
    public void xixi() {
        System.out.println("AfterReturning ...");
    }

    @Around("aopPointCut()")
    public void xxx(ProceedingJoinPoint pj) {
        try {
            System.out.println("Around aaa ...");
            pj.proceed();
            System.out.println("Around bbb ...");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Pointcut("execution(* org.aspectj.service.IBuy.buyPrice(double)) && args(price) && bean(girl)")
    public void gift(double price) {
    }

    /**
     * around notice
     * @param pj, price
     */
    /*
    @Around("gift(price)")
    public String hihi(ProceedingJoinPoint pj, double price) {
        try {
            // System.out.println("Around aaa ...");
            pj.proceed();
            if (price > 68) {
                System.out.println("女孩买衣服超过了68元，赠送一双袜子");
                return "衣服和袜子";
            }
            // System.out.println("Around bbb ...");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "衣服";
    }
    */
}
