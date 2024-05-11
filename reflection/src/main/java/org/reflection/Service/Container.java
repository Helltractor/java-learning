package org.reflection.Service;

import org.reflection.annotation.Autowired;
import org.reflection.annotation.Bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: helltractor
 * @Date: 2024/5/3 18:10
 */

public class Container {
    private Map<Class<?>, Method> methods;
    private Object config;

    private Map<Class<?>, Object> services;

    /**
     * Initialize the container
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void init() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.methods = new HashMap<>();
        this.services = new HashMap<>();
        Class<?> clazz = Class.forName("org.reflection.config.Configuration");
        Method[] methods = clazz.getDeclaredMethods();
        // Get all methods with @Bean annotation
        for (Method method : methods) {
            if (method.getAnnotation(Bean.class) != null) {
                // Put the method into the map
                this.methods.put(method.getReturnType(), method);
            }
        }
        this.config = clazz.getConstructor().newInstance();
    }

    /**
     * Get service instance by class
     * @param clazz
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object getServiceInstanceByClass(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        if (this.services.containsKey(clazz)) {
            return this.services.get(clazz);
        } else {
            if (this.methods.containsKey(clazz)) {
                Method method = this.methods.get(clazz);
                Object obj = method.invoke(this.config);
                this.services.put(clazz, obj);
                return obj;
            }
        }
        return null;
    }

    /**
     * Create instance
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public Object createInstance(Class<?> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            // If the constructor has @Autowired annotation
            if (constructor.getAnnotation(Autowired.class) != null) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameters[i] = getServiceInstanceByClass(parameterTypes[i]);
                }
                return constructor.newInstance(parameters);
            }
        }
        return clazz.getConstructor().newInstance();
    }
}
