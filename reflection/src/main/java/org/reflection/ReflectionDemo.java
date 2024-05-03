package org.reflection;

import org.reflection.annotation.MyAnnotation;
import org.reflection.entity.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: helltractor
 * @Date: 2024/5/3 16:00
 */

public class ReflectionDemo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        // 访问 User 对象的静态字段和方法
//        int field = User.publicStaticField;
//        System.out.println("publicStaticField: " + field);
//        User.myPublicStaticMethod();

        // 访问 User 对象的字段和方法
//        User user = new User();
//        System.out.println("name: " + user.name);
//        user.myPublicMethod();

        // 获取 class 对象的三种方式
        // 通过类名.class获取, 静态引用, 编译时获取, 适用于知道类名的情况
//        Class<User> userClass = User.class;

        // 通过对象.getClass()获取, 动态引用, 运行时获取, 采用通配符适用于不知道类名的情况
//        User user = new User("helltractor", 11);
//        Class<?> clazz = user.getClass();

        // 通过Class.forName()获取, 动态引用, 编译时获取, 适用于知道类名的情况
        Class<?> clazz = Class.forName("org.reflection.entity.User");

        // 获取 public 和 private 字段
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            System.out.println("Field: " + field.getName());
//        }

        // 获取 public 字段
//        Field[] fields = clazz.getFields();
//        for (Field field : fields) {
//            System.out.println("Field: " + field.getName());
//        }

        // 获取父类的字段
//        Field[] superFields = clazz.getSuperclass().getDeclaredFields();
//        for (Field field : superFields) {
//            System.out.println("Field: " + field.getName());
//        }

        // 获取指定字段
//        Field field = clazz.getDeclaredField("name");
//        System.out.println("Field: " + field.getName());
//        System.out.println("Type: " + field.getType()); // 获取字段类型
//        System.out.println("Annotation: " + field.getDeclaredAnnotation(MyAnnotation.class)); // 获取注解
//
//        Field field1 = clazz.getDeclaredField("age");
//        System.out.println("Field: " + field1.getName());
//        System.out.println("Type: " + field1.getType()); // 获取字段类型
//        System.out.println("Annotation: " + field1.getDeclaredAnnotation(MyAnnotation.class)); // 获取注解
//
//        Field field2 = clazz.getDeclaredField("comments");
//        System.out.println("Field: " + field2.getName());
//        System.out.println("Type: " + field2.getType()); // 获取字段类型, 会发生类型擦除
//        System.out.println("GenericType: " + field2.getGenericType()); // 获取字段类型, 不会发生类型擦除
//        System.out.println("Annotation: " + field2.getDeclaredAnnotation(MyAnnotation.class)); // 获取注解

        // NoSuchFieldException, 因为通过反射获取字段是在运行时动态执行的， 而不是编译时，所以编译时的错误在运行时不会报错
        // Field field3 = clazz.getDeclaredField("address22");

        // 获取static字段值
//        Field field = clazz.getDeclaredField("publicStaticField");
//        System.out.println("Field: " + field.getName());
//        System.out.println("Value: " + field.get(null)); // 获取static字段值
//
          // illegalAccessException, 因为privateStaticField是私有的，需要设置访问权限
//        Field field1 = clazz.getDeclaredField("privateStaticField");
//        System.out.println("Field: " + field1.getName());
//        field1.setAccessible(true); // 设置访问权限
//        field1.set(null, 30); // 设置static字段值
//        System.out.println("Value: " + field1.get(null)); // 获取static字段值

        // 获取方法
//        Method[] methods = clazz.getDeclaredMethods();
//        for (Method method : methods) {
//            System.out.println("Method: " + method.getName());
//        }

        // static method, 无需实例化
//        Method method = clazz.getDeclaredMethod("myPublicStaticMethod");
//        method.invoke(null);
//
//        Method method1 = clazz.getDeclaredMethod("myPrivateStaticMethod");
//        method1.setAccessible(true);
//        method1.invoke(null);
//
//        Method method2 = clazz.getDeclaredMethod("myPublicStaticMethod", String.class);
//        method2.invoke(null, "hello");

        // 实例
        Constructor<?> constructor = clazz.getConstructor();
        Constructor<?> constructor1 = clazz.getConstructor(String.class, Integer.class);
        Object obj = constructor1.newInstance("helltractor", 11); // 运行时，无法识别对象类型
        Field field = clazz.getDeclaredField("age");
        field.setAccessible(true);
        field.set(obj, 99);
        System.out.println("Age: " + field.get(obj));

        // 类型转换
//        if (obj instanceof User) {
//            User user = (User) obj;
//        }

        // cast, 通过使用泛型实现类型转换，需要在编译时进行
//        Class<User> clazz1 = User.class;
//        Constructor<?> constructor = clazz.getConstructor(String.class, Integer.class);
//        Object obj = constructor.newInstance("helltractor", 11); // 运行时，无法识别对象类型
//        User user = clazz1.cast(obj);

    }
}
