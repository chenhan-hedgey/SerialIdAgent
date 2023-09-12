package org.chenhan.serialAgent.domain.agent.service.matcher.impl;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.domain.agent.service.transfomer.MethodTransformerGenerator;
import org.chenhan.serialAgent.domain.context.model.po.AgentConfig;
import org.chenhan.serialAgent.domain.context.service.config.SysConfig;
import org.chenhan.serialAgent.exception.AgentException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/7 16:35
 **/
public class ParseElementMatcherGeneratorTest {

    @Test
    public void test_parseCondition() throws AgentException {
        List<String> list = new ArrayList<>();
        list.add("java.lang.String.class");
        list.add("java.util.Map.class");
        list.add("java.util.ArrayList.class");
        list.add("java.util.List.class");
        list.add("java.lang.String[].class");
        list.add("named(java.util.Map.class,int.class,java.lang.String[].class)");
        list.add("named(java.util.List.class,int.class,java.lang.String.class)");
        list.add("named(java.util.Map.class,int.class,int[].class)");
        list.add("named(java.util.Map.class,java.util.ArrayList.class,java.lang.String[].class)");
        ParseElementMatcherGenerator parseElementMatcherGenerator = new ParseElementMatcherGenerator();
        List<ElementMatcher> matchers = new ArrayList<>();
        for (String s : list) {
            ElementMatcher build = parseElementMatcherGenerator.build(s);
            matchers.add(build);
            System.out.println(build);
        }
    }
    @Test
    public void test_transformer() throws AgentException {
        String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";

        SysConfig sysConfig = SysConfig.getSingleton();
        sysConfig.loadConfig(path,false);
        MethodTransformerGenerator methodTransformerGenerator = new MethodTransformerGenerator(new ParseElementMatcherGenerator());
        String interceptMethod = sysConfig.getAgentConfig().getInterceptMethod();
        String[] stringArray = AgentConfig.getStringArray(interceptMethod);
        methodTransformerGenerator.setMethodInfo(stringArray[1]);
        AgentBuilder.Transformer transformer = methodTransformerGenerator.builderTransformer();
        //methodTransformerGenerator.setMethodInfo();
    }
    @Test
    public void test_typeMatcher() throws AgentException {
        String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";

        SysConfig sysConfig = SysConfig.getSingleton();
        sysConfig.loadConfig(path,false);
        // class name
        ParseElementMatcherGenerator parseElementMatcherGenerator = new ParseElementMatcherGenerator();

        String className = sysConfig.getAgentConfig().getInterceptClassString();
        ElementMatcher typeMatcher = parseElementMatcherGenerator.build(className);
        System.out.println(className);
        System.out.println(typeMatcher);
    }
}