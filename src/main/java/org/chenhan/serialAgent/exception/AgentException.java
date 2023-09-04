package org.chenhan.serialAgent.exception;

/**
 * @Author: chenhan
 * @Description: 资源加载时出现的异常
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 11:05
 */
public class AgentException extends Exception {

    public AgentException(String message) {
        super(message);
    }

    public AgentException(String message, Throwable cause) {
        super(message, cause);
    }
}