package org.chenhan.serialAgent.domain.agent.service.intercept;

import org.chenhan.serialAgent.domain.context.AgentContext;
import org.chenhan.serialAgent.exception.AgentException;
import org.junit.Test;
import org.tools.serialNumber.SerialThreadData;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/8 11:10
 **/
public class SerialInterceptorTest {

    /**
     * 从AgentContext中获取Method方法
     */
    @Test
    public void test_getCallMethod() throws AgentException {
        String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";


        AgentContext agentContext = new AgentContext(path);

        agentContext.loadContext();
        System.out.println(SerialInterceptor.getCallMethod(agentContext));
    }

    @Test
    public void test_processArgs() {
        try {
        String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";
            HashMap<String, String> map = new HashMap<>();
            map.put("chenhan","chen wan zhou");
            SerialThreadData.threadData.set(map);


        AgentContext singleton = AgentContext.singleton();
        singleton.fullPathAndLoad(path);

        singleton.loadContext();
            List<Object> objects = SerialInterceptor.processArgs(new Object[]{"陈含", "陈冲"});


            System.out.println(objects);
        } catch (AgentException e) {

        }

    }
}