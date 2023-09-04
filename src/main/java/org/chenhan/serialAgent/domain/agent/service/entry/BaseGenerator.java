package org.chenhan.serialAgent.domain.agent.service.entry;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.commons.lang3.ObjectUtils;
import org.chenhan.serialAgent.exception.AgentException;

import java.lang.instrument.Instrumentation;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 13:55
 */
public class BaseGenerator implements AgentGenerator {
    public BaseGenerator() {
        this(new AgentBuilder.Default());
    }

    public BaseGenerator(AgentBuilder agentBuilder) {

    }

    public BaseGenerator(AgentBuilder agentBuilder, AgentBuilder.Listener listener, ElementMatcher elementMatcher, AgentBuilder.Transformer transformer) {
        this.agentBuilder = agentBuilder;
        this.listener = listener;
        this.elementMatcher = elementMatcher;
        this.transformer = transformer;
    }

    /**
     * agent对象实例
     */
    private AgentBuilder agentBuilder;
    /**
     * listener实例
     */
    private AgentBuilder.Listener listener;
    /**
     * 匹配类型
     */
    private ElementMatcher elementMatcher;
    /**
     * transformer实例
     */
    private AgentBuilder.Transformer transformer;
    /**
     * 安装agent
     *
     * @param instrumentation Instrumentation对象
     */
    @Override
    public void installAgent(Instrumentation instrumentation) {
        necessaryCheck();
        if (listener==null){
            agentBuilder.type(elementMatcher).transform(transformer).installOn(instrumentation);
        }
        else{
            agentBuilder.with(listener).type(elementMatcher).transform(transformer).installOn(instrumentation);
        }
    }

    /**
     * 可运行检查的必须性检查
     */
    private void necessaryCheck() throws AgentException {
        if (this.agentBuilder == null ) {

        }
    }


}
