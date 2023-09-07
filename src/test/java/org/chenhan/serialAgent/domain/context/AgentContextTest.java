package org.chenhan.serialAgent.domain.context;

import org.chenhan.serialAgent.domain.context.service.config.SysConfig;
import org.chenhan.serialAgent.exception.AgentException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/7 15:37
 **/
public class AgentContextTest {

    @Test
    public void loadContext() throws AgentException {
        String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";
        SysConfig singleton = SysConfig.getSingleton();
        singleton.loadConfig(path,false);
        AgentContext agentContext = new AgentContext(singleton);
        agentContext.loadContext();
        System.out.println(agentContext);
    }
}