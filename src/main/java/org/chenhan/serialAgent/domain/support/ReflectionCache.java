package org.chenhan.serialAgent.domain.support;

/**
 * @Author: chenhan
 * @Description: 反射缓存池，缓存通过class.forName找到的方法、类、字段等
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 9:42
 */
public class ReflectionCache {
    /**
     * 通过字符串获取class实例
     * @param className 全限定名
     * @return class实例
     */
    public static Class loadForClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}
