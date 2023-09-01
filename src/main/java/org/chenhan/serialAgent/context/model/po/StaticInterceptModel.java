package org.chenhan.serialAgent.context.model.po;

import lombok.Data;

/**
 * @Author: chenhan
 * @Description: 拦截的类以及对应的重定向方法
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 12:57
 */
@Data
public class StaticInterceptModel{
    /**
     * 待拦截的类
     */
    private String originClassName;
    /**
     * 待拦截的静态方法
     */
    private String originMethodName;
    /**
     * 拦截的函数签名
     */
    private String[] originMethodArgs;
    /**
     * 重定向的类
     */
    private String targetClassName;
    /**
     * 待拦截的静态方法
     */
    private String targetMethodName;
    /**
     * 拦截的函数签名
     */
    private String[] targetMethodArgs;
}
