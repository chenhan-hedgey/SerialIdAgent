package org.chenhan.serialAgent.domain.support;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.util.ContextInitializer;
import org.chenhan.serialAgent.exception.AgentException;
import org.slf4j.LoggerFactory;

/**
 * @Author: chenhan
 * @Description: logback的配置
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/13 18:09
 **/
public class LogbackConfig {
    /**
     * 通过代码配置,加载路径
     * @param configFile
     */
    public static void configureLogback(String configFile) throws AgentException {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset(); // 清除先前的配置
        //ContextInitializer ci = new ContextInitializer(lc);
        //ci.setAutoScan(false);
        //ci.setVariable("log_dir", logDir);
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        try {
            // 加载新的配置文件
            configurator.doConfigure(configFile);
        } catch (Exception e) {
            throw new AgentException("日志配置出错",e);
        }
    }
}