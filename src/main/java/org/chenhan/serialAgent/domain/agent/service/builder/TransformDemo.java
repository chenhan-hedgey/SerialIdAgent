package org.chenhan.serialAgent.domain.agent.service.builder;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.chenhan.serialAgent.domain.agent.service.intercept.SerialInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: chenhan
 * @Description: transformer
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/2 12:14
 */
public class TransformDemo implements AgentBuilder.Transformer{

    private static final Logger logger = LoggerFactory.getLogger(TransformDemo.class);
    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
        // 匹配指定的函数签名
        logger.info("class:{},已进入transform方法",typeDescription.getCanonicalName());
        builder = builder
                .method(ElementMatchers.named("call")
                        .and(ElementMatchers.takesArguments(String[].class))
                        .and(ElementMatchers.isStatic()))
                .intercept(MethodDelegation.to(SerialInterceptor.class));
        return builder;
    }
}
