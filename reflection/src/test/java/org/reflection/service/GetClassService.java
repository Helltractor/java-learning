package org.reflection.service;

import org.reflection.entity.User;

/**
 * 获得Class对象
 */
public class GetClassService {

    /**
     * 通过类名.class获取, 静态引用, 编译时获取, 适用于知道类名的情况
     * @return
     */
    public static Class<?> getClassByStatic() {
        return Object.class;
    }

    /**
     * 通过对象实例.getClass()获取, 动态引用, 运行时获取, 采用通配符适用于不知道类名的情况
     */
    public Class<?> getClassByInstance(Object object) {
        return object.getClass();
    }

    /**
     * 通过Class.forName()获取, 动态引用, 编译时获取, 适用于知道类名的情况
     */
    public static Class<?> getClassForName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}