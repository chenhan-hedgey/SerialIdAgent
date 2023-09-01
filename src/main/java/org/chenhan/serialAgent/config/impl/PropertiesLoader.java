package org.chenhan.serialAgent.config.impl;

import org.chenhan.serialAgent.config.SourceLoader;
import org.chenhan.serialAgent.exception.LoaderException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: chenhan
 * @Description: properties文件的加载
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 11:00
 */
public class PropertiesLoader implements SourceLoader {
    /**
     * 从指定的完整路径加载资源，并将其内容作为键值对形式的 Map 返回。
     *
     * @param fullPath 资源的完整路径，需要被加载。
     * @return 包含加载资源内容的键值对形式的 Map。
     * @throws LoaderException 如果加载资源时出现异常。
     */
    @Override
    public Map<String, String> load(String fullPath) throws LoaderException{

        Properties properties = new Properties();
        Map<String, String> resultMap = new HashMap<>(10);

        try (FileInputStream inputStream = new FileInputStream(fullPath)) {
            properties.load(inputStream);

            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                resultMap.put(key, value);
            }
        } catch (IOException e) {
            throw new LoaderException("读取properties文件时出错",e);
        }

        return resultMap;
    }

}
