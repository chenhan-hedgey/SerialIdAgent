package org.chenhan.serialAgent.domain.parse.model.node;

import net.bytebuddy.matcher.ElementMatcher;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: devtools
 * @Date: 2023/9/4 19:19
 */

public class AndNode extends BinaryOperationNode {

    public AndNode() {
        nodeType =  "and";
    }

    /**
     * 抽象方法，实现二元操作符的特异操作
     *
     * @return
     */
    @Override
    protected ElementMatcher.Junction operate() {
        return left.evaluate().and(right.evaluate());
    }
}
