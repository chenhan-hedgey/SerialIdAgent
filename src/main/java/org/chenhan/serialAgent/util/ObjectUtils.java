package org.chenhan.serialAgent.util;

/**
 * @Author: chenhan
 * @Description: 对象工具类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 14:06
 */
public class ObjectUtils {
    /**
     * 判断对象实例是否为null
     *
     * @param object 要检查的对象
     * @return 如果对象实例为null，则返回true，否则返回false
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判断多个对象实例中是否有任何一个为null
     *
     * @param objects 要检查的多个对象
     * @return 如果多个对象实例中有任何一个为null，则返回true，否则返回false
     */
    public static boolean isAnyNull(Object... objects) {
        if (objects == null) {
            return true;
        }

        for (Object object : objects) {
            if (isNull(object)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断多个对象实例是否都为null。
     *
     * @param objects 要检查的多个对象
     * @return 如果多个对象实例都为null，则返回true，否则返回false
     */
    public static boolean isAllNull(Object... objects) {
        // 如果传入的数组为null，直接返回true
        if (objects == null) {
            return true;
        }

        // 遍历对象数组，只要有一个对象不为null就返回false
        for (Object object : objects) {
            if (!isNull(object)) {
                return false;
            }
        }

        // 所有对象都为null，返回true
        return true;
    }

}

