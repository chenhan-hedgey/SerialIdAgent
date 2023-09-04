package org.chenhan.serialAgent.domain.agent.service.matcher.impl;

import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.domain.agent.service.matcher.ElementMatcherGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 14:54
 */
public class ParseElementMatcherGenerator implements ElementMatcherGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ParseElementMatcherGenerator.class);

    /**
     * 条件map
     */
    private String conditions;


    /**
     * 生成对应的ElementMatcher
     *
     * @param condition 参数
     * @return ElementMatcher实例
     */
    @Override
    public ElementMatcher build(String condition) {
        return parseCondition(condition);
    }

    /**
     * todo:增加解析类
     * @param condition
     * @return
     */
    private ElementMatcher parseCondition(String condition) {
        return null;
    }
}
