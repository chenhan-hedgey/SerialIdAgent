package org.chenhan.serialAgent.domain.agent.service.matcher.impl;

import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.exception.AgentException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
}