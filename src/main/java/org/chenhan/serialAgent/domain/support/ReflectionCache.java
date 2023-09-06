package org.chenhan.serialAgent.domain.support;

import org.apache.commons.lang3.StringUtils;
import org.chenhan.serialAgent.exception.AgentException;

import java.lang.reflect.Array;
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
    public static Class loadClass(String className) throws ClassNotFoundException, AgentException {
        if (StringUtils.isBlank(className)){
           throw new AgentException("类名不存在");
        }
        Class result = null;
        // 1.从缓存数据中直接获取
        result = getClass(className);
        // 2. 如果数据不存在
        if (result==null) {
            if (className.endsWith(".class")){
                className = className.substring(0,className.length()-6);
            }
            result = realLoad(className);
            putClass(className,result);
        }
        return result;
    }

    /**
     *
     * @param className 不为空且不要包含.class
     * @return
     * @throws ClassNotFoundException
     */
    private static Class realLoad(String className) throws ClassNotFoundException {
        Class result = null;
        if (className.endsWith("[]")){
            result = realLoadArray(className);
        }
        else{
            result = Class.forName(className);
        }
        return result;
    }

    /**
     * 传入一个数组形式的对象并加载
     * @param className 如java.lang.String[]形式字符串
     * @return 生成对应形式的class数组
     */
    private static Class<?> realLoadArray(String className) throws ClassNotFoundException {
        String singleClass = className.substring(0, className.length() - 2);
        Class<?> single = Class.forName(singleClass);
        Class<?> arrayClass = Array.newInstance(single, 0).getClass();
        return arrayClass;
    }

    private static void putClass(String className, Class result) {
        if (!className.endsWith(".class")) {
            className += ".class";
        }
        classCache.put(className,result);
    }

    private static Class getClass(String className) {
        if (!className.endsWith(".class")){
            className += ".class";
        }
        return classCache.get(className);
    }
}
