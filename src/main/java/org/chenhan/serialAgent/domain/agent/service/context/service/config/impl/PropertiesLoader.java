package org.chenhan.serialAgent.domain.agent.service.context.service.config.impl;

import org.chenhan.serialAgent.domain.agent.context.service.config.SourceLoader;
import org.chenhan.serialAgent.domain.agent.service.context.service.config.BaseSourceLoader;
import org.chenhan.serialAgent.exception.AgentException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: chenhan
 * @Description: properties文件的加载
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 11:00
 */
public class PropertiesLoader extends BaseSourceLoader {
    /**
     * 从指定的完整路径加载资源，并将其内容作为键值对形式的 Map 返回。
     *
     * @param fullPath 资源的完整路径，需要被加载。
     * @return 包含加载资源内容的键值对形式的 Map。
     * @throws AgentException 如果加载资源时出现异常。
     */
    @Override
    public Map<String, String> load(String fullPath) throws AgentException {

        Properties properties = new Properties();
        Map<String, String> resultMap = new HashMap<>(10);

        try (FileInputStream inputStream = new FileInputStream(fullPath)) {
            properties.load(inputStream);

            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                resultMap.put(key, value);
            }
        } catch (IOException e) {
            throw new AgentException("读取properties文件时出错",e);
        }

        return resultMap;
    }

}
