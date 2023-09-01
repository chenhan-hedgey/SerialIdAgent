package org.chenhan.serialAgent.config;

import org.chenhan.serialAgent.exception.LoaderException;

import java.util.Map;
import java.util.Properties;

/**
 * @Author: chenhan
 * @Description: 资源加载器
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 10:55
 */
public interface SourceLoader {
    /**
     * 从指定的完整路径加载资源，并将其内容作为键值对形式的 Map 返回。
     *
     * @param fullPath 资源的完整路径，需要被加载。
     * @return 包含加载资源内容的键值对形式的 Map。
     * @throws LoaderException 如果加载资源时出现异常。
     */
    public  Map<String, String> load(String fullPath) throws LoaderException;
}
