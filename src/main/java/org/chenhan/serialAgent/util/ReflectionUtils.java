package org.chenhan.serialAgent.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenhan
 * @Description: 反射对应的工具类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 9:35
 */

public class ReflectionUtils {
    /**
     * 调用指定的静态方法
     *
     * @param method    要调用的静态方法的 Method 对象
     * @param arguments 方法的实际参数列表
     * @return 调用方法后的返回值
     * @throws InvocationTargetException 如果调用方法时发生异常
     * @throws IllegalAccessException    如果无法访问方法
     * @throws IllegalArgumentException  如果方法不是静态方法
     */
    public static Object callStaticMethod(Method method, Object[] arguments) throws InvocationTargetException, IllegalAccessException {
        // 校验方法是否为静态方法
        if (!isStaticMethod(method)) {
            throw new IllegalArgumentException("Method is not a static method");
        }

        // 调用指定的方法并返回结果
        return callMethod(null,method,arguments);
    }
    public static Object callStaticMethod(Method method, List<Object> arguments) throws InvocationTargetException, IllegalAccessException {
        if (arguments==null){
            arguments = new ArrayList<>();
        }
        // 调用指定的方法并返回结果
        return callStaticMethod(method,arguments.toArray());
    }

    public static Object callMethod(Object instance,Method method, Object[] arguments) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(instance, arguments);
    }

    /**
     * 判断方法是否为静态方法
     *
     * @param method 要判断的方法
     * @return 如果方法是静态方法返回 true，否则返回 false
     */
    private static boolean isStaticMethod(Method method) {
        return (method.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0;
    }

    /**
     * 获取指定类中的静态方法。
     *
     * @param targetClass 要查找方法的类
     * @param functionName 方法名
     * @param paramsType 方法参数类型数组
     * @return 对应的静态方法的 Method 对象
     * @throws NoSuchMethodException 如果找不到对应的方法
     */
    public static Method getStaticMethodFromClass(Class<?> targetClass, String functionName, Class<?>[] paramsType) throws NoSuchMethodException {
        try {
            // 尝试获取声明的静态方法
            return targetClass.getDeclaredMethod(functionName, paramsType);
        } catch (NoSuchMethodException e) {
            // 如果找不到，尝试获取可访问的静态方法（包括继承的方法）
            return targetClass.getMethod(functionName, paramsType);
        }
    }
    /**
     * 通过 class 实例获取指定的 Field 对象
     *
     * @param clazz     类的 Class 对象
     * @param fieldName 要获取的字段名称
     * @return Field 对象，用于访问字段的属性和值
     * @throws NoSuchFieldException 如果未找到指定名称的字段
     */
    public static Field getFieldFromClass(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        // 设置字段为可访问，以便获取和设置值
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    /**
     * 依据传递的 class 类型，获取指定的字段值
     *
     * @param object 对象实例（可以为 null，因为这里是处理静态字段）
     * @param field  要获取值的字段
     * @param clazz  返回值的类型
     * @param <T>    泛型类型参数
     * @return 字段的值，根据传入的类型进行转换
     * @throws IllegalAccessException 如果无法访问字段的值
     */
    public static <T> T getValueFromField(Object object, Field field, Class<T> clazz) throws IllegalAccessException {
        // 获取字段的值，将获取的值转换为指定类型
        Object value = field.get(object);
        return castWithNull(value, clazz);
    }

    public static <T> T getValueFromStaticField( Field field, Class<T> clazz) throws IllegalAccessException {
        // 获取字段的值，将获取的值转换为指定类型
        return getValueFromField(null, field, clazz);
    }

    /**
     * 设置指定的字段值
     *
     * @param object 对象实例（可以为 null，因为这里是处理静态字段）
     * @param field  要设置值的字段
     * @param value  要设置的值
     * @param <T>    泛型类型参数
     * @throws IllegalAccessException 如果无法访问字段或设置字段的值
     */
    public static <T> void setValueToField(Object object, Field field, T value) throws IllegalAccessException {
        // 设置字段的值
        field.set(object, value);
    }

    /**
     * 转换对象至目标类型
     *
     * @param origin 原实例
     * @param targetClass 目标类型的class
     * @return 转化后的实例对象
     * @param <T>
     */
    public static <T> T castWithNull(Object origin, Class<T> targetClass) {
        if (origin == null) {
            return null;
        }
        // 尝试进行转换
        try {
            T result = targetClass.cast(origin);
            return result;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Cannot cast object to the specified target type", e);
        }
    }
}
