package org.chenhan.serialAgent.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 12:41
 */import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilsTest {
    /**
     * 测试 Strings2Classes 方法，用于将字符串数组转换为 Class 对象数组。
     * 此方法测试了不同类型的类名字符串。
     * 预期结果是检查输出的 Class 对象是否与输入字符串数组中的类名相对应。
     *
     * @throws ClassNotFoundException 如果任何一个输入的全限定名无法找到对应的 Class 对象，则抛出 ClassNotFoundException。
     */
    @Test
    public void testStrings2Classes() throws ClassNotFoundException {
        // 定义测试输入
        String[] classNames = {"java.lang.String", "java.util.Map", "int", "double[]"};

        // 调用被测试的方法
        Class<?>[] classes = StringUtils.Strings2Classes(classNames);

        // 断言测试结果
        assertEquals(4, classes.length);
        assertEquals(String.class, classes[0]);
        assertEquals(java.util.Map.class, classes[1]);
        assertEquals(int.class, classes[2]);
        assertEquals(double[].class, classes[3]);
    }

    /**
     * 测试 String2Class 方法，用于将单个类名字符串转换为 Class 对象。
     * 此方法测试了不同类型的类名字符串。
     * 预期结果是检查输出的 Class 对象是否与输入字符串相对应。
     *
     * @throws ClassNotFoundException 如果输入的全限定名无法找到对应的 Class 对象，则抛出 ClassNotFoundException。
     */
    @Test
    public void testString2Class() throws ClassNotFoundException {
        // 定义测试输入
        String className1 = "java.lang.String";
        String className2 = "int";
        String className3 = "boolean[]";

        // 调用被测试的方法
        Class<?> clazz1 = StringUtils.String2Class(className1);
        Class<?> clazz2 = StringUtils.String2Class(className2);
        Class<?> clazz3 = StringUtils.String2Class(className3);

        // 断言测试结果
        assertEquals(String.class, clazz1);
        assertEquals(int.class, clazz2);
        assertEquals(boolean[].class, clazz3);
    }
}
