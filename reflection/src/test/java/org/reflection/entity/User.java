package org.reflection.entity;

import java.util.List;

/**
 * @Author: helltractor
 * @Date: 2024/5/3 15:51
 */

public class User extends Person {

    public String name = "helltractor";
    private int age = 11;
    private List<String> comments;
    private Message message;
    private final String address = "Beijing";
    public static int publicStaticField = 10;
    private static int privateStaticField = 20;

    // 静态代码块，访问静态成员或创建类的实例时执行
    static {
        System.out.println("User static block");
    }

    // 构造代码块，每次创建类的实例时执行
    public User() {
        System.out.println("User constructor");
    }

    // 构造代码块，每次创建含参数的类实例时执行
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
        System.out.println("User constructor with name and age");
    }

    public void myPublicMethod() {
        System.out.println("This is a public method");
    }

    private void myPrivateMethod() {
        System.out.println("This is a private method");
    }

    public void myPublicMethod(String str) {
        System.out.println("This is a public method with string parameter: " + str);
    }

    private void myPrivateMethod(String str) {
        System.out.println("This is a private method with string parameter: " + str);
    }

    public static void myPublicStaticMethod() {
        System.out.println("This is a public static method");
    }

    private static void myPrivateStaticMethod() {
        System.out.println("This is a private static method");
    }

    public static void myPublicStaticMethod(String str) {
        System.out.println("This is a public static method with string parameter: " + str);
    }

}
