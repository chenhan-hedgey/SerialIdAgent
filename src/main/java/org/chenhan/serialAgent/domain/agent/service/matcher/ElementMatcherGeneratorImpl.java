package org.chenhan.serialAgent.domain.agent.service.matcher;

import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 14:54
 */
public class ElementMatcherGeneratorImpl implements ElementMatcherGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ElementMatcherGeneratorImpl.class);

    /**
     * 条件map
     */
    private Map<String,String> conditions;


    /**
     * 生成对应的ElementMatcher
     *
     * @param condition 条件
     * @return ElementMatcher实例
     */
    @Override
    public ElementMatcher build(String condition) {
        ElementMatcher elementMatcher = parseCondition(condition);
        return elementMatcher;
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
