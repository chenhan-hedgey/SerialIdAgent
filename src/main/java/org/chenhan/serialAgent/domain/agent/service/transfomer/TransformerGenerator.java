package org.chenhan.serialAgent.domain.agent.service.transfomer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.Transformer;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: chenhan
 * @Description: 创建Transformer对象实例
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 14:29
 */
public interface TransformerGenerator {
    /**
     * 生成transformer对象
     * @return 生成的对象实例
     */
    AgentBuilder.Transformer builderTransformer();

    /**
     * transform模板
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MethodTransformer implements  AgentBuilder.Transformer{
        private static final Logger logger = LoggerFactory.getLogger(MethodTransformer.class);
        /**
         * method拦截方法
         */
        ElementMatcher elementMatcher;
        /**
         * interceptor
         */
        MethodDelegation methodDelegation;


        /**
         * Allows for a transformation of a {@link DynamicType.Builder}.
         *
         * @param builder         The dynamic builder to transform.
         * @param typeDescription The description of the type currently being instrumented.
         * @param classLoader     The class loader of the instrumented class. Might be {@code null} to represent the bootstrap class loader.
         * @param module          The class's module or {@code null} if the current VM does not support modules.
         * @return A transformed version of the supplied {@code builder}.
         */
        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
            logger.info("transform中，拦截的类型：{}",typeDescription.getCanonicalName());
            return builder.method(elementMatcher).intercept(methodDelegation);
        }
    }
}
