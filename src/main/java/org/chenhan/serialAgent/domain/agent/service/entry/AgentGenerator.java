package org.chenhan.serialAgent.domain.agent.service.entry;

import java.lang.instrument.Instrumentation;

/**
 * @Author: chenhan
 * @Description: Agent生成器
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 13:51
 */
public interface AgentGenerator {
    /**
     * 安装agent
     * @param instrumentation Instrumentation对象
     */
    void installAgent(Instrumentation instrumentation);
}
