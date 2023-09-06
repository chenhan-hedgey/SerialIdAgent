package org.chenhan.serialAgent.domain.parse.model.node;

import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.domain.parse.model.arg.Argument;

import java.util.List;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: devtools
 * @Date: 2023/9/4 19:53
 */
public class FunctionNode extends BaseExpressionNode {
    public FunctionNode() {
        nodeType = "function";
    }

    /**
     * elementMatchers中对应的函数名
     */
    private String function;

    /**
     * function所需的argument参数列表
     */
    private List<Argument> arguments;
    /**
     * 抽象方法，实现操作符的特异操作
     * todo:实现operate
     * @return 生成对应的Junction对象
     */
    @Override
    protected ElementMatcher.Junction operate() {
        return null;
    }



}
