package org.chenhan.serialAgent.domain.context.service.config;

import org.chenhan.serialAgent.exception.AgentException;

import java.util.Map;

/**
 * @Author: chenhan
 * @Description: 配置校验
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 12:59
 */
public interface ValidateSource {
    /**
     * 校验配置文件是否配置正确
     * @param map 配置项
     * @return 配置文件的状态
     * @throws AgentException 自定义异常
     */
    public Integer validate(Map<String,String> map) throws AgentException;

    /**
     * 默认的校验器
     */
    public class Default implements ValidateSource {
        /**
         * 校验配置文件是否配置正确
         * 0-一切正常，1-加载失败，更多状态可待设置
         * @param map 配置项
         * @return 配置文件的状态
         * @throws AgentException 自定义异常
         */
        @Override
        public Integer validate(Map<String, String> map) throws AgentException {
            return 0;
        }
    }
}
