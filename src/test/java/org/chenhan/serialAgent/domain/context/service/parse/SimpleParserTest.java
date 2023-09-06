package org.chenhan.serialAgent.domain.context.service.parse;

import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.domain.support.ReflectionCache;
import org.chenhan.serialAgent.exception.AgentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/6 14:02
 **/
public class SimpleParserTest {


    @Test
    public void testValidMethodFormat() throws AgentException {
        SimpleParser parser = new SimpleParser("XXXX.XXX.class#method(java.lang.String[].class,int.class,double[].class)");
        // 测试有效的方法格式
        ElementMatcher.Junction matcher = parser.parse();
        assertNotNull(matcher);
        // 在这里可以添加更多断言来验证生成的匹配器是否符合预期
    }

    @Test
    public void testInvalidMethodFormat() {
        // 测试无效的方法格式
        SimpleParser invalidParser = new SimpleParser("InvalidMethodFormat");
        try {
            invalidParser.parse();
            fail("预期抛出 AgentException 异常");
        } catch (AgentException e) {
            // 预期抛出 IllegalArgumentException 异常
        }
    }
}