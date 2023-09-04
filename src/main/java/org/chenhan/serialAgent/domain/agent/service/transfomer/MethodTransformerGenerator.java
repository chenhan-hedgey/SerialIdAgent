package org.chenhan.serialAgent.domain.agent.service.transfomer;

import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import org.chenhan.serialAgent.domain.agent.service.intercept.SerialInterceptor;
import org.chenhan.serialAgent.domain.agent.service.matcher.ElementMatcherGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 14:33
 */
public class MethodTransformerGenerator implements TransformerGenerator {
    ElementMatcherGenerator elementMatcherGenerator;




    /**
     * 生成transformer对象
     *
     * @return 生成的对象实例
     */
    @Override
    public DynamicType.Builder builderTransformer() {
        String conditions = null;
        MethodTransformer methodTransformer = MethodTransformer.builder()
                        .elementMatcher(elementMatcherGenerator.build(conditions))
                        .methodDelegation(MethodDelegation.to(SerialInterceptor.class))
                        .build();

        return null;
    }
}
