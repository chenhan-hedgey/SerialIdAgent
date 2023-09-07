package org.chenhan.serialAgent.domain.context;

import ch.qos.logback.core.boolex.Matcher;
import lombok.Data;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.chenhan.serialAgent.domain.context.service.config.SysConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: chenhan
 * @Description: agent上下文类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 10:53
 */
@Data
public class AgentContext {

    /**
     * 待拦截的类文件实例
     */
    Class originClass;
    /**
     * 待拦截的方法匹配器
     */
    ElementMatcher.Junction methodMatcher;
    /**
     * 重定向的类
     */
    Class targetClass;
    /**
     * 重定向的方法
     */
    Method targetMethod;
    /**
     * 数据字段
     */
    Field[] data;
    /**
     * 系统配置
     */
    SysConfig sysConfig;

    public void loadContext(){

    }

}
