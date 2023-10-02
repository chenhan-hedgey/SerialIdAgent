package org.chenhan.serialAgent.domain.support;

import org.apache.commons.lang3.StringUtils;
import org.chenhan.serialAgent.exception.AgentException;
import org.chenhan.serialAgent.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: chenhan
 * @Description: 反射缓存池，缓存通过class.forName找到的方法、类、字段等
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 9:42
 */
public class ReflectionCache {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionCache.class);
    /**
     * class的缓存
     */
    private static Map<String, Class> classCache = new ConcurrentHashMap<>();

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
     *
     * @param className 全限定名
     * @return class实例
     */
    public static Class loadClass(String className) throws ClassNotFoundException, AgentException {
        if (StringUtils.isBlank(className)) {
            throw new AgentException("类名不存在");
        }
        Class result = null;
        // 1.从缓存数据中直接获取
        result = getClass(className);
        // 2. 如果数据不存在
        if (result == null) {
            if (className.endsWith(".class")) {
                className = className.substring(0, className.length() - 6);
            }
            result = realLoad(className);
            putClass(className, result);
        }
        return result;
    }

    /**
     * @param className 不为空且不要包含.class
     * @return
     * @throws ClassNotFoundException
     */
    private static Class realLoad(String className) throws ClassNotFoundException {
        Class result = null;
        if (className.endsWith("[]")) {
            result = realLoadArray(className);
        } else {
            result = Class.forName(className);
        }
        return result;
    }

    /**
     * 传入一个数组形式的对象并加载
     *
     * @param className 如java.lang.String[]形式字符串
     * @return 生成对应形式的class数组
     */
    private static Class<?> realLoadArray(String className) throws ClassNotFoundException {
        String singleClass = className.substring(0, className.length() - 2);
        Class<?> single = Class.forName(singleClass);
        Class<?> arrayClass = Array.newInstance(single, 0).getClass();
        return arrayClass;
    }

    /**
     * 将类名和对应的Class对象放入类缓存中，支持处理类名后缀为“.class”。
     *
     * @param className 类名
     * @param result    对应的Class对象
     */
    private static void putClass(String className, Class result) {
        if (!className.endsWith(".class")) {
            className += ".class";
        }
        classCache.put(className, result);
    }

    /**
     * 根据类名从类缓存中获取对应的Class对象，支持处理类名后缀为“.class”。
     *
     * @param className 类名
     * @return 对应的Class对象，如果未找到则返回null
     */
    private static Class getClass(String className) {
        if (!className.endsWith(".class")) {
            className += ".class";
        }
        return classCache.get(className);
    }

    /**
     * 从agentContext中获取指定类的方法。
     *
     * @param classAndMethod 包含类名和方法名的字符串
     * @return 方法的Method对象
     * @throws AgentException 如果未找到指定类或方法，抛出异常
     */
    public static Method getCallMethod(String classAndMethod) throws AgentException {
        String[] split = org.chenhan.serialAgent.util.StringUtils.splitComponent(classAndMethod);
        String className = split[0];
        String methodName = split[1];
        Class klass = null;
        Class[] args = null;
        try {
            // 加载指定类名的Class对象
            klass = ReflectionCache.loadClass(split[0]);

            if (split.length == 3) {
                // 如果包含参数类型信息，将参数类型字符串转换为Class对象数组
                args = ReflectionCache.getArgClassArray(split[2].split(","));
            }
        } catch (ClassNotFoundException e) {
            // 记录错误日志，未找到指定类
            logger.error("未找到指定类", e);
            throw new AgentException("未找到指定类", e);
        }

        // 加载指定类的指定方法
        Method result = ReflectionCache.loadMethod(klass, methodName, args);
        return result;
    }

    /**
     * 将参数类型字符串数组转换为对应的Class对象数组，并支持异常处理。
     *
     * @param argsClass 参数类型字符串数组
     * @return 参数类型的Class对象数组
     * @throws AgentException        如果加载类时出现异常，抛出异常
     * @throws ClassNotFoundException 如果未找到类时抛出异常
     */
    public static Class[] getArgClassArray(String[] argsClass) throws AgentException, ClassNotFoundException {
        Class[] classes = new Class[argsClass.length];
        for (int i = 0; i < argsClass.length; i++) {
            // 加载参数类型字符串对应的Class对象
            classes[i] = loadClass(argsClass[i]);
        }
        return classes;
    }

    /**
     * 加载指定类的静态方法的Method对象，支持缓存。
     *
     * @param tClazz      包含方法的类
     * @param tMethodName 方法名
     * @param argClasses  方法的参数类型数组
     * @return 方法的Method对象
     * @throws AgentException 如果未找到方法或出现其他异常，抛出异常
     */
    public static Method loadMethod(Class tClazz, String tMethodName, Class[] argClasses) throws AgentException {
        String key = getMethodKey(tClazz, tMethodName, argClasses);
        Method orDefault = methodCache.getOrDefault(key, null);
        if (orDefault == null) {
            orDefault = realLoadMethod(tClazz, tMethodName, argClasses);
        }
        return orDefault;
    }


    /**
     * 生成唯一的方法缓存键，格式为“类名#方法名(参数类型列表)”。
     *
     * @param tClazz      包含方法的类
     * @param tMethodName 方法名
     * @param argClasses  方法的参数类型数组
     * @return 方法缓存键
     */
    public static String getMethodKey(Class tClazz, String tMethodName, Class[] argClasses) {
        String key = tClazz.getSimpleName() + "#" + tMethodName + "(" + Arrays.toString(argClasses) + ")";
        return key;
    }


    /**
     * 实际加载指定类的静态方法的Method对象，并支持缓存。
     *
     * @param tClazz      包含方法的类
     * @param tMethodName 方法名
     * @param argClasses  方法的参数类型数组
     * @return 方法的Method对象
     * @throws AgentException 如果未找到方法或出现其他异常，抛出异常
     */
    private static Method realLoadMethod(Class tClazz, String tMethodName, Class[] argClasses) throws AgentException {
        Method method = null;
        try {
            // 从类中获取静态方法
            method = ReflectionUtils.getStaticMethodFromClass(tClazz, tMethodName, argClasses);

            if (method == null) {
                // 记录错误日志，找到的静态方法为null
                logger.error("class:{},method:{},argType:{}找到的静态方法为null",tClazz.getSimpleName(),tMethodName,Arrays.toString(argClasses));
                throw new NoSuchMethodException(tClazz.getSimpleName() + tMethodName + ":没有找到...");
            }

            // 构建唯一的方法缓存键，格式为“类名#方法名(参数类型列表)”
            String methodCacheKey = getMethodKey(tClazz, tMethodName, argClasses);

            // 将方法对象放入方法缓存中
            methodCache.put(methodCacheKey, method);
        } catch (NoSuchMethodException e) {
            // 记录错误日志，未找到该静态方法
            throw new AgentException("没有找到该静态方法", e);
        }

        return method;
    }


    /**
     * 加载指定类的静态字段的Field对象，支持缓存。
     *
     * @param clazz     包含字段的类
     * @param fieldName 字段名
     * @return 字段的Field对象
     * @throws AgentException 如果未找到字段或出现其他异常，抛出异常
     */
    public static Field loadField(Class clazz, String fieldName) throws AgentException {
        Field field = null;

        // 构建唯一的字段缓存键，格式为“类名#字段名”
        String fieldCacheKey = clazz.getSimpleName() + "#" + fieldName;

        // 从字段缓存中获取字段对象
        field = getField(fieldCacheKey);

        if (field == null) {
            try {
                // 如果字段缓存中不存在，尝试从类中获取字段
                field = ReflectionUtils.getFieldFromClass(clazz, fieldName);
            } catch (NoSuchFieldException e) {
                // 记录错误日志，未找到字段
                logger.error("未找到字段 {}#{}", clazz.getSimpleName(), fieldName);
                throw new AgentException("没有该静态字段", e);
            }
        }

        return field;
    }


    /**
     * 获取指定字段名的Field对象，支持缓存。
     *
     * @param name 字段名
     * @return 字段的Field对象
     * @throws AgentException 如果字段名为空或未找到字段，抛出异常
     */
    private static Field getField(String name) throws AgentException {
        if (name == null || name.length() == 0) {
            // 记录错误日志，字段名为空
            logger.error("字段名为空:{}",name);
            throw new AgentException("字段名为空");
        }

        Field field = fieldCache.get(name);
        if (field == null) {
            // 记录错误日志，未找到字段
            logger.error("未找到字段: {}", name);
            throw new AgentException("未找到字段: " + name);
        }

        return field;
    }

}
