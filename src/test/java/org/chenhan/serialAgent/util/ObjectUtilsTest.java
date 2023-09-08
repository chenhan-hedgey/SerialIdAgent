package org.chenhan.serialAgent.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 14:14
 */
public class ObjectUtilsTest {


    @Test
    public void testIsNull() {
        // 测试一个非null对象
        assertFalse(ObjectUtils.isNull(new Object()));

        // 测试一个null对象
        assertTrue(ObjectUtils.isNull(null));
    }

    @Test
    public void testIsAnyNull() {
        // 测试多个对象中有一个为null的情况，预期结果为true
        assertTrue(ObjectUtils.isAnyNull("Hello", 42, null, true));

        // 测试多个对象都不为null的情况，预期结果为false
        assertFalse(ObjectUtils.isAnyNull("Hello", 42, true));

        // 测试多个对象都为null的情况，预期结果为true
        assertTrue(ObjectUtils.isAnyNull(null, null, null));

        // 测试空数组情况，预期结果为true
        assertTrue(ObjectUtils.isAnyNull());
    }

    @Test
    public void testIsAllNull() {
        // 测试多个对象都为null的情况，预期结果为true
        assertTrue(ObjectUtils.isAllNull(null, null, null));

        // 测试多个对象中有一个不为null的情况，预期结果为false
        assertFalse(ObjectUtils.isAllNull(null, 42, null, "World"));

        // 测试多个对象都不为null的情况，预期结果为false
        assertFalse(ObjectUtils.isAllNull("Hello", 42, true));

        // 测试空数组情况，预期结果为true
        assertTrue(ObjectUtils.isAllNull());
    }

}