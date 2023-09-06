package org.chenhan.serialAgent.domain.parse.model.node;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: devtools
 * @Date: 2023/9/5 8:59
 */
public abstract class SingleOperatorNode extends BaseExpressionNode {
    ExpressionNode child;



    /**
     * 清空计算值
     */
    @Override
    public void clear() {
        child.clear();
        junctionCache = null;
        return;
    }
}
