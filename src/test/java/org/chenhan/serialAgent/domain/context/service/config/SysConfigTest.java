package org.chenhan.serialAgent.domain.context.service.config;

import org.chenhan.serialAgent.exception.AgentException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/7 12:28
 **/
public class SysConfigTest {


    @Test
    public void loadConfig() {

    }

    @Test
    public void load() throws AgentException {
        String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";
        SysConfig sysConfig = SysConfig.getSingleton();
        sysConfig.loadConfig(path,false);
        System.out.println(sysConfig);
    }
}