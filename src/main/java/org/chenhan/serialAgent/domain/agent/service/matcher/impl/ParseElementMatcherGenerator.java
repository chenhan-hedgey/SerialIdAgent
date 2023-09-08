package org.chenhan.serialAgent.domain.agent.service.matcher.impl;

import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.chenhan.serialAgent.domain.agent.service.matcher.ElementMatcherGenerator;
import org.chenhan.serialAgent.domain.context.service.parse.Parser;
import org.chenhan.serialAgent.domain.context.service.parse.SimpleMethodParser;
import org.chenhan.serialAgent.exception.AgentException;
import org.chenhan.serialAgent.util.StringUtils;
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
     * 条件语句
     */
    private String conditions;


    /**
     * 生成对应的ElementMatcher
     *
     * @param condition 参数
     * @return ElementMatcher实例
     */
    @Override
    public ElementMatcher build(String condition) throws AgentException {
        return parseCondition(condition);
    }

    /**
     * todo:增加解析类
     * @param condition xxx.xxx.xxxx.class or method(XXX,xxX)
     * @return
     */
    private ElementMatcher parseCondition(String condition) throws AgentException {
        if (StringUtils.isBlank(condition)) {
            logger.info("字符串为空，condition:{}",condition);
            throw new AgentException("字符串为null");
        }
        if (condition.contains("(")&&condition.endsWith(")")){
            Parser parser = new SimpleMethodParser(condition);
            return parser.parse();
        } else if (condition.endsWith(".class")) {
            return ElementMatchers.named(condition.substring(0, condition.length() - 6));
        }
        else{
            logger.info("字符串格式错误,condition:{}",condition);
            throw new AgentException("格式错误");
        }
    }


}
