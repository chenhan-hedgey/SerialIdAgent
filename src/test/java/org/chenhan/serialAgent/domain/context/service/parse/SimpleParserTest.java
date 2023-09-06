package org.chenhan.serialAgent.domain.context.service.parse;

import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.domain.support.ReflectionCache;
import org.chenhan.serialAgent.exception.AgentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/6 14:02
 **/
public class SimpleParserTest {
    private SimpleParser parser;

    @Before
    public void setUp() {
        // 在每个测试方法运行之前初始化 SimpleParser 实例
        parser = new SimpleParser("XXX.XXX.XXX.class#method(String.class,String[].class)");
    }

    @Test
    public void testValidMethodFormat() throws AgentException {
        // 测试有效的方法格式
        ElementMatcher.Junction matcher = parser.parse();
        assertNotNull(matcher);
        // 在这里可以添加更多断言来验证生成的匹配器是否符合预期
    }

    @Test
    public void testInvalidMethodFormat() {
        // 测试无效的方法格式
        SimpleParser invalidParser = new SimpleParser("InvalidMethodFormat");
        try {
            invalidParser.parse();
            fail("预期抛出 AgentException 异常");
        } catch (AgentException e) {
            // 预期抛出 IllegalArgumentException 异常
        }
    }

    @Test
    public void testBasicTypes() {
        // 测试获取基本数据类型的类对象
        try {
            Class<?> booleanClass = ReflectionCache.loadClass("boolean");
            Class<?> charClass = ReflectionCache.loadClass("char");
            Class<?> byteClass = ReflectionCache.loadClass("byte");
            Class<?> shortClass = ReflectionCache.loadClass("short");
            Class<?> intClass = ReflectionCache.loadClass("int");
            Class<?> longClass = ReflectionCache.loadClass("long");
            Class<?> floatClass = ReflectionCache.loadClass("float");
            Class<?> doubleClass = ReflectionCache.loadClass("double");

            assertEquals(boolean.class, booleanClass);
            assertEquals(char.class, charClass);
            assertEquals(byte.class, byteClass);
            assertEquals(short.class, shortClass);
            assertEquals(int.class, intClass);
            assertEquals(long.class, longClass);
            assertEquals(float.class, floatClass);
            assertEquals(double.class, doubleClass);

        } catch (ClassNotFoundException e) {
            fail("未能成功加载基本数据类型的类对象");
        }
    }

    /**
     * 测试数组，包括基本数据类型数组，对象类型数组
     */
    @Test
    public void testArray(){

    }

    @Test
    public void testMap(){
        Map<String,String> map ;//= new HashMap<>();
        Class<?> klass = null;
        try {
            klass = Class.forName("java.util.Map");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(klass, Map.class);
    }

    @Test
    public void testInt() {
        System.out.println(int.class);
        System.out.println(int[].class);
        System.out.println(int.class);
        System.out.println(int.class);
    }

    @Test
    public void test(){
        try {
            System.out.println(Class.forName("[I"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}