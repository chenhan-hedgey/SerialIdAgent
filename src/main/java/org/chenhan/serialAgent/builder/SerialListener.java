package org.chenhan.serialAgent.builder;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 15:58
 */
public class SerialListener implements AgentBuilder.Listener {
    private static final Logger agentLogger = LoggerFactory.getLogger(SerialListener.class);

    @Override
    public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
        //agentLogger.info("Discovered class: {}", s);
    }

    @Override
    public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
        agentLogger.info("Transformed class: {}", typeDescription.getName());
    }

    @Override
    public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
        //agentLogger.info("Ignored class: {}", typeDescription.getName());
    }

    @Override
    public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
        agentLogger.error("Error occurred: {}", s, throwable);
    }

    @Override
    public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
        //agentLogger.info("Instrumentation complete for class: {}", s);
    }
}
