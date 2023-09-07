package org.chenhan.serialAgent.domain.context.service.parse;

import net.bytebuddy.matcher.ElementMatcher;
import org.chenhan.serialAgent.exception.AgentException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/6 14:02
 **/
public class SimpleMethodParserTest {


    @Test
    public void testValidMethodFormat() throws AgentException {
        SimpleMethodParser parser = new SimpleMethodParser("method(java.lang.String[].class,int.class,double[].class,java.util.Map)");
        // 测试有效的方法格式
        ElementMatcher.Junction matcher = parser.parse();
        assertNotNull(matcher);
        // 在这里可以添加更多断言来验证生成的匹配器是否符合预期
    }

    @Test
    public void testInvalidMethodFormat() {
        // 测试无效的方法格式
        SimpleMethodParser invalidParser = new SimpleMethodParser("InvalidMethodFormat");
        try {
            invalidParser.parse();
            fail("预期抛出 AgentException 异常");
        } catch (AgentException e) {
            // 预期抛出 IllegalArgumentException 异常
        }
        catch (Exception e){

        }
    }
}