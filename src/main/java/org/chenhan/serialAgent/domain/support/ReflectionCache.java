package org.chenhan.serialAgent.domain.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: chenhan
 * @Description: 反射缓存池，缓存通过class.forName找到的方法、类、字段等
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 9:42
 */
public class ReflectionCache {
    /**
     * class的缓存
     */
    private static Map<String,Class> classCache = new ConcurrentHashMap<>();
    static {
        // 基本类型
        classCache.put("int.class", int.class);
        classCache.put("byte.class", byte.class);
        classCache.put("short.class", short.class);
        classCache.put("long.class", long.class);
        classCache.put("float.class", float.class);
        classCache.put("double.class", double.class);
        classCache.put("char.class", char.class);
        classCache.put("boolean.class", boolean.class);
        classCache.put("void.class", void.class);

        // 基本类型的数组
        classCache.put("int[].class", int[].class);
        classCache.put("byte[].class", byte[].class);
        classCache.put("short[].class", short[].class);
        classCache.put("long[].class", long[].class);
        classCache.put("float[].class", float[].class);
        classCache.put("double[].class", double[].class);
        classCache.put("char[].class", char[].class);
        classCache.put("boolean[].class", boolean[].class);

    }
    /***
     * method的缓存
     */
    private static Map<String, Method> methodCache = new ConcurrentHashMap<>();
    /**
     * field的缓存
     */
    private static Map<String, Field> fieldCache = new ConcurrentHashMap<>();
    /**
     * 通过字符串获取class实例
     * @param className 全限定名
     * @return class实例
     */
    public static Class loadClass(String className) throws ClassNotFoundException {
        Class result = null;
        result = classCache.get(className);
        if (result==null) {
            result = Class.forName(className);
            classCache.put(className,result);
        }
        return result;
    }
}
