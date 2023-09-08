package org.chenhan.serialAgent.domain.parse.model.node;

import net.bytebuddy.matcher.ElementMatcher;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: devtools
 * @Date: 2023/9/4 19:30
 */
public abstract class BaseExpressionNode implements ExpressionNode {

    String nodeType = "base";
    /**
     * 如果计算过那就不再计算
     */
    ElementMatcher.Junction junctionCache;

    /**
     * 清空计算值
     */
    @Override
    public void clear() {
        junctionCache = null;
    }
    /**
     * 根据不同的条件生对应的Junction类
     *
     * @return 生成Junction类
     */
    @Override
    public ElementMatcher.Junction evaluate() {
        if (junctionCache==null){
            junctionCache = operate();
        }
        return junctionCache;
    }



    /**
     * 抽象方法，实现操作符的特异操作
     * @return 生成对应的Junction对象
     */
    protected abstract ElementMatcher.Junction operate();

}
