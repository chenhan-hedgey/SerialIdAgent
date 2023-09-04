package org.chenhan.serialAgent.util;

import java.lang.reflect.Array;

/**
 * @Author: chenhan
 * @Description: 字符串处理工具类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 12:30
 */
public class StringUtils {
    /**
     * 根据字符串的全限定名获取对应的class对象实例数组。
     *
     * @param classNames 包含类全限定名的字符串数组，用于获取对应的Class对象。
     *                   例如，["java.lang.String", "java.util.Map"]
     * @return 返回一个Class对象数组，包含了与输入字符串数组中的全限定名相对应的Class对象。
     *         如果找不到任何一个类的Class对象，则对应位置为null。
     * @throws ClassNotFoundException 如果任何一个输入的全限定名无法找到对应的Class对象，则抛出ClassNotFoundException。
     */
    public static Class<?>[] Strings2Classes(String[] classNames) throws ClassNotFoundException {
        if (classNames == null || classNames.length == 0) {
            return new Class<?>[0];
        }

        Class<?>[] classArray = new Class<?>[classNames.length];

        for (int i = 0; i < classNames.length; i++) {
            String className = classNames[i];
            if (className != null && !className.isEmpty()) {
                classArray[i] = String2Class(className);
            } else {
                classArray[i] = null;
            }
        }

        return classArray;
    }

    /**
     * 根据字符串的全限定名获取对应的class对象实例。
     *
     * @param className 类的全限定名字符串，用于获取对应的Class对象。
     *                  例如，"java.lang.String" 或 "java.lang.String[]"
     * @return 返回与输入全限定名相对应的Class对象，如果找不到则返回null。
     * @throws ClassNotFoundException 如果输入的全限定名无法找到对应的Class对象，则抛出ClassNotFoundException。
     * todo:暂未考虑基本类型
     */
    public static Class<?> String2Class(String className) throws ClassNotFoundException {
        if (className == null || className.isEmpty()) {
            return null;
        }

        if (className.endsWith("[]")) {
            // 处理数组类型
            String componentType = className.substring(0, className.length() - 2);
            Class<?> componentClass = String2Class(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            }
        } else {
            // 处理普通类
            return Class.forName(className);
        }

        return null;
    }

}
