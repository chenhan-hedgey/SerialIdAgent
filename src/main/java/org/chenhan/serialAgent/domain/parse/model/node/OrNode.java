package org.chenhan.serialAgent.domain.parse.model.node;

import net.bytebuddy.matcher.ElementMatcher;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: devtools
 * @Date: 2023/9/4 19:52
 */
public class OrNode extends BinaryOperationNode {
    public OrNode() {
        nodeType = "or";
    }

    /**
     * 抽象方法，实现二元操作符的特异操作
     *
     * @return 生成对应的Junction对象
     */
    @Override
    protected ElementMatcher.Junction operate() {
        return left.evaluate().or(right.evaluate());
    }
}
