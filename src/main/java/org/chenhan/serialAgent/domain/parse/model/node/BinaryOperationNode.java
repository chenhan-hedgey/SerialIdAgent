package org.chenhan.serialAgent.domain.parse.model.node;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: devtools
 * @Date: 2023/9/4 19:18
 */


public abstract class BinaryOperationNode extends BaseExpressionNode {
    public BinaryOperationNode() {
        nodeType = "binaryOperator";
    }

    /**
     * 左节点
     */
    ExpressionNode left;
    /**
     * 右节点
     */
    ExpressionNode right;

    /**
     * 抽象方法，实现二元操作符的特异操作
     *
     * @return 生成对应的Junction对象
     */
    @Override
    public void clear() {
        left.clear();
        right.clear();
        junctionCache = null;
    }
}
