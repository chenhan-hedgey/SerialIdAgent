package org.chenhan.serialAgent.domain.parse.model.node;

import net.bytebuddy.matcher.ElementMatcher;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: devtools
 * @Date: 2023/9/5 9:01
 */
public class NotOperator extends SingleOperatorNode {
    /**
     * 抽象方法，实现操作符的特异操作
     *
     * @return 生成对应的Junction对象
     */
    @Override
    protected ElementMatcher.Junction operate() {
        return null;
    }
}
