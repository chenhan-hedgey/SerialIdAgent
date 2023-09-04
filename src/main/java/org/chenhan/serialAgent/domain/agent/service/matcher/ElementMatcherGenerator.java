package org.chenhan.serialAgent.domain.agent.service.matcher;

import net.bytebuddy.matcher.ElementMatcher;

import java.util.Map;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 14:57
 */
public interface ElementMatcherGenerator {

    /**
     * 生成对应的ElementMatcher
     *
     * @param condition 条件map
     * @return ElementMatcher实例
     */
    public ElementMatcher build(String condition) ;
}
