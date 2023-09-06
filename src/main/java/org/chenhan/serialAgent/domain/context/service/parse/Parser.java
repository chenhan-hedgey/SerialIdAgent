package org.chenhan.serialAgent.domain.context.service.parse;

import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.exception.AgentException;

/**
 * @Author: chenhan
 * @Description: 转换配置文件为ElementMatcher.Junction
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/6 9:21
 **/
public interface Parser {
    /**
     * 转化配置文件为Junction
     * @return junction实例
     * @throws AgentException 自定义异常
     */
    ElementMatcher.Junction parse() throws AgentException;
}
