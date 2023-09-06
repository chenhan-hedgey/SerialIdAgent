package org.chenhan.serialAgent.domain.parse.model.node;

import net.bytebuddy.matcher.ElementMatcher;

/**
 * @Author: chenhan
 * @Description: 节点基类
 * @ProjectName: devtools
 * @Date: 2023/9/4 19:13
 */
public interface ExpressionNode {
    /**
     * 根据不同的条件生对应的Junction类
     * @return 生成Junction类
     */
    ElementMatcher.Junction evaluate();
    /**
     * 清空计算值
     */
    void clear();
}
