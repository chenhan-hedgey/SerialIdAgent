package org.chenhan.serialAgent.domain.parse.service.matcher;

import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.domain.parse.model.node.ExpressionNode;

/**
 * @Author: chenhan
 * @Description: ElementMatcher生成器
 * @ProjectName: devtools
 * @Date: 2023/9/5 16:32
 **/
public class MatcherBuilder {
    /**
     * 根据文法生产的结果产生匹配器
     * @param node 根节点
     * @return Junction对象
     */
    ElementMatcher.Junction buildMatcher(ExpressionNode node){
        return node.evaluate();
    }
}
