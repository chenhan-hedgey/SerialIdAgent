package org.chenhan.serialAgent.domain.agent.service.transfomer;

import lombok.Data;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.domain.agent.service.intercept.SerialInterceptor;
import org.chenhan.serialAgent.domain.agent.service.matcher.impl.CustomElementMatcherGenerator;
import org.chenhan.serialAgent.domain.agent.service.matcher.ElementMatcherGenerator;
import org.chenhan.serialAgent.exception.AgentException;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 14:33
 */
@Data
public class MethodTransformerGenerator implements TransformerGenerator {
    ElementMatcherGenerator elementMatcherGenerator;

    /**
     * 函数信息
     */
    String methodInfo;

    public MethodTransformerGenerator() {
        this(new CustomElementMatcherGenerator());
    }

    public MethodTransformerGenerator(ElementMatcherGenerator elementMatcherGenerator) {
        this.elementMatcherGenerator = elementMatcherGenerator;
    }

    /**
     * 生成transformer对象
     *
     * @return 生成的对象实例
     */
    @Override
    public AgentBuilder.Transformer builderTransformer() throws AgentException {
        ElementMatcher elementMatcher = elementMatcherGenerator.build(methodInfo);
        MethodTransformer methodTransformer = MethodTransformer.builder()
                        .elementMatcher(elementMatcher)
                        .methodDelegation(MethodDelegation.to(SerialInterceptor.class))
                        .build();

        return methodTransformer;
    }
}
