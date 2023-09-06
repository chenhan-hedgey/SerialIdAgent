package org.chenhan.serialAgent.domain.support;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/6 16:53
 **/
public class ReflectionCacheTest {

    @Test
    /**
     * 测试基本类型的获取
     */
    public void loadClassTest_BasicType(){
        try {

            Assert.assertEquals(int.class, ReflectionCache.loadClass("int.class"));
            Assert.assertEquals(byte.class, ReflectionCache.loadClass("byte.class"));
            Assert.assertEquals(short.class, ReflectionCache.loadClass("short.class"));
            Assert.assertEquals(long.class, ReflectionCache.loadClass("long.class"));
            Assert.assertEquals(float.class, ReflectionCache.loadClass("float.class"));
            Assert.assertEquals(double.class, ReflectionCache.loadClass("double.class"));
            Assert.assertEquals(char.class, ReflectionCache.loadClass("char.class"));
            Assert.assertEquals(boolean.class, ReflectionCache.loadClass("boolean.class"));
            // 特殊类型
            Assert.assertEquals(void.class, ReflectionCache.loadClass("void.class"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void test() throws ClassNotFoundException {
        // 测试基本类型数组
        Assert.assertEquals(int[].class, ReflectionCache.loadClass("int[].class"));
        Assert.assertEquals(byte[].class, ReflectionCache.loadClass("byte[].class"));
        Assert.assertEquals(short[].class, ReflectionCache.loadClass("short[].class"));
        Assert.assertEquals(long[].class, ReflectionCache.loadClass("long[].class"));
        Assert.assertEquals(float[].class, ReflectionCache.loadClass("float[].class"));
        Assert.assertEquals(double[].class, ReflectionCache.loadClass("double[].class"));
        Assert.assertEquals(char[].class, ReflectionCache.loadClass("char[].class"));
        Assert.assertEquals(boolean[].class, ReflectionCache.loadClass("boolean[].class"));
    }

}