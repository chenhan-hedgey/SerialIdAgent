package org.chenhan.serialAgent.exception;

/**
 * @Author: chenhan
 * @Description: 资源加载时出现的异常
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 11:05
 */
public class LoaderException extends Exception {

    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}