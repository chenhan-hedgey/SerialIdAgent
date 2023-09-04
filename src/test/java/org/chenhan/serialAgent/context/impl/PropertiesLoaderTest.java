package org.chenhan.serialAgent.context.impl;

import org.chenhan.serialAgent.domain.agent.service.context.service.config.SourceLoader;
import org.chenhan.serialAgent.domain.agent.service.context.service.config.impl.PropertiesLoader;
import org.chenhan.serialAgent.exception.AgentException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 11:15
 */
public class PropertiesLoaderTest {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesLoaderTest.class);
    @Test
    public void load() {
        SourceLoader sourceLoader = new PropertiesLoader();
        try {
            Map<String,String> map = sourceLoader.load("C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties");
            logger.info("读取文件的map配置:{}",map);
        } catch (AgentException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loadWithException() {
        SourceLoader sourceLoader = new PropertiesLoader();
        try {
            Map<String,String> map = sourceLoader.load("Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties");
            logger.info("读取文件的map配置:{}",map);
        } catch (AgentException e) {
            e.printStackTrace();
        }
    }
}