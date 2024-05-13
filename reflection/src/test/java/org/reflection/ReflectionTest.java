package org.reflection;

import org.junit.Test;
import org.reflection.annotation.MyAnnotation;
import org.reflection.entity.User;
import org.reflection.service.GetClassService;;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {

    // 通过Class.forName()获取
    String className = "org.reflection.entity.User";
    Class <?> clazz = GetClassService.getClassForName(className);

    public ReflectionTest() throws ClassNotFoundException {
    }

    /**
     * 获取public和private字段
     * @throws ClassNotFoundException
     */
    @Test
    public void getDeclaredField() throws ClassNotFoundException {
        // 获取 public 和 private 字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("Field: " + field.getName());
        }
    }

    /**
     * 获取public字段
     * @throws ClassNotFoundException
     */
    @Test
    public void getFields() throws ClassNotFoundException {
        // 获取 public 字段
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println("Field: " + field.getName());

        }
    }

    /**
     * 获取父类的字段
     * @throws ClassNotFoundException
     */
    @Test
    public void getSuperFields() throws ClassNotFoundException {
        // 获取父类的字段
        Field[] superFields = clazz.getSuperclass().getDeclaredFields();
        for (Field field : superFields) {
            System.out.println("Field: " + field.getName());
        }
    }

    /**
     * 获取指定字段
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     */
    @Test
    public void getDeclaredFieldByAnnotation() throws ClassNotFoundException, NoSuchFieldException {
        // 获取指定字段
        Field field = clazz.getDeclaredField("name");
        System.out.println("Field: " + field.getName());
        System.out.println("Type: " + field.getType()); // 获取字段类型
        System.out.println("Annotation: " + field.getDeclaredAnnotation(MyAnnotation.class)); // 获取注解

        Field field2 = clazz.getDeclaredField("comments");
        System.out.println("Field: " + field2.getName());
        System.out.println("Type: " + field2.getType()); // 获取字段类型, 会发生类型擦除
        System.out.println("GenericType: " + field2.getGenericType()); // 获取字段类型, 不会发生类型擦除
        System.out.println("Annotation: " + field2.getDeclaredAnnotation(MyAnnotation.class)); // 获取注解

//         NoSuchFieldException, 因为通过反射获取字段是在运行时动态执行的， 而不是编译时，所以运行时的错误在编译时不会报错
//         Field field3 = clazz.getDeclaredField("address22");
    }

    /**
     * 获取static字段值
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void getStaticField() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        // 获取static字段值
        Field field = clazz.getDeclaredField("publicStaticField");
        System.out.println("Field: " + field.getName());
        System.out.println("Value: " + field.get(null)); // 获取static字段值

//         illegalAccessException, 因为privateStaticField是私有的，需要设置访问权限
        Field field1 = clazz.getDeclaredField("privateStaticField");
        System.out.println("Field: " + field1.getName());
        field1.setAccessible(true); // 设置访问权限
        field1.set(null, 30); // 设置static字段值
        System.out.println("Value: " + field1.get(null)); // 获取static字段值
    }

    /**
     * 获取方法
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Test
    public void getDeclaredMethod() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // 获取方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method.getName());
        }

        // static method,无需实例化
        Method method = clazz.getDeclaredMethod("myPublicStaticMethod");
        method.invoke(null);

        Method method1 = clazz.getDeclaredMethod("myPrivateStaticMethod");
        method1.setAccessible(true);
        method1.invoke(null);

        Method method2 = clazz.getDeclaredMethod("myPublicStaticMethod", String.class);
        method2.invoke(null, "hello");
    }

    /**
     * 类型转换,通过Class.cast()方法实现, 在编译时实现
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    @Test
    public  void cast() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class<?> clazzStatic = User.class;
        Constructor<?> constructor = clazzStatic.getDeclaredConstructor(String.class, Integer.class);
        Object obj = constructor.newInstance("helltractor", 11); // 运行时，无法识别对象类型， 传入Object类型
        if (obj instanceof User) {
            User user = (User) obj;
        }
        Object user = clazzStatic.cast(obj);
    }

    /**
     * 类型转换, 通过使用泛型实现类型转换
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @Test
    public  void reflect() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Constructor<?> constructor = clazz.getConstructor(String.class, Integer.class);
        Object obj = constructor.newInstance("helltractor", 11); // 运行时，无法识别对象类型

        // 获取字段, 设置字段值, 即使是private字段也可以通过反射设置，破坏了封装性
        Field field = clazz.getDeclaredField("age");
        field.setAccessible(true);
        field.set(obj, 99);
        System.out.println("Age: " + field.get(obj));

        // 获取方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method.getName());
        }
    }
}
