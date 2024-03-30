package org.reflection;
import java.lang.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionDemoPerson {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException {

        // 获取 Person 类的 Class 对象
        Class<?> personClass = Class.forName("org.reflection.entity.Person");

        // 获取并打印类名
        System.out.println("类名：" + personClass.getName());

        // 获取构造函数
        Constructor<?> constructor = personClass.getConstructor(String.class, int.class);

        // 使用构造函数创建 Person 对象实例
        Object personInstance = constructor.newInstance("乌龟", 30);

        // 获取并调用 getName 方法
        Method getNameMethod = personClass.getMethod("getName");
        String name = (String) getNameMethod.invoke(personInstance);
        System.out.println("名字：" + name);

        // 获取并调用 setAge 方法
        Method setAgeMethod = personClass.getMethod("setAge", int.class);
        setAgeMethod.invoke(personInstance, 35);

        // 获取并访问 age 字段
        Field ageField = personClass.getDeclaredField("age");
        ageField.setAccessible(true);
        int age = ageField.getInt(personInstance);
        System.out.println("年纪: " + age);

        // 获取并调用私有方法
        Method privateMethod = personClass.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true);
        privateMethod.invoke(personInstance);
    }
}
