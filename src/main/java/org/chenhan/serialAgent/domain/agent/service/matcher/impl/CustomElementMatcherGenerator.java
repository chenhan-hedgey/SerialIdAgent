package org.chenhan.serialAgent.domain.agent.service.matcher.impl;

import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.chenhan.serialAgent.domain.agent.service.matcher.ElementMatcherGenerator;

import static net.bytebuddy.matcher.ElementMatchers.*;
/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 15:22
 */
public class CustomElementMatcherGenerator implements ElementMatcherGenerator {

    /**
     * 生成对应的ElementMatcher
     *
     * @param condition 参数
     * @return ElementMatcher实例
     */
    @Override
    public ElementMatcher build(String condition) {
        if ("type".equals(condition)) {
            return named("org.tools.mockAPI.ApiCaller");
        }
        else if ("method".equals(condition)){
            return named("call")
                    .and(takesArguments(String[].class))
                    .and(isStatic());
        }
        else{
            return null;
        }
    }
}
