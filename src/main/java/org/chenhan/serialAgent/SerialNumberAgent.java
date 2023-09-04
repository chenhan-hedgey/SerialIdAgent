package org.chenhan.serialAgent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.chenhan.serialAgent.domain.agent.service.builder.SerialListener;
import org.chenhan.serialAgent.domain.agent.service.builder.TransformDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * @Author: chenhan
 * @Description: premain类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 10:09
 */
public class SerialNumberAgent {
    private static final Logger logger = LoggerFactory.getLogger(SerialNumberAgent.class);
    /**
     * premain方法，执行agent的配置逻辑
     * @param arg 传递的命令行参数，传递配置文件地址
     * @param instrumentation Instrumentation对象，用于在Java应用程序启动时转换字节码
     */
    public static void premain(String arg, Instrumentation instrumentation)  {
        try {
            String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";
            arg = path;
            logger.info("流水号Agent开始执行,配置路径为:{}...",arg);

            // 1.加载配置文件，初始化配置
            if (StringUtils.isBlank(arg)) {
                logger.error("配置文件路径为空，请检查路径");
                return;
            }


            // 2.配置agent
            AgentBuilder.Default agentBuilder = new AgentBuilder.Default();
            logger.info("是否执行到了transformer实例化");
            logger.info("是否执行到了agent安装");
            agentBuilder
                    // 配置监听类
                    .with(new SerialListener())
                    // 拦截指定类
                    .type(ElementMatchers.named("org.tools.mockAPI.ApiCaller"))
                    // 加载拦截器
                    .transform(new TransformDemo())
                    // 安装instrumentation
                    .installOn(instrumentation);
            // 3.安装agent
            logger.info("流水号Agent结束执行...");
        }
        catch (Exception e) {
            // 获取异常堆栈跟踪
            String errorMessage = e.getMessage();
            // 获取异常堆栈跟踪
            String stackTrace = ExceptionUtils.getStackTrace(e);
            logger.error("安装agent出错 - 错误信息: {}\n堆栈跟踪:\n{}", errorMessage, stackTrace);
        }

    }

    /**
     * 校验文件路径
     * @param path 配置文件的路径
     */
    private static void validateProperties(String path) {
    }

    /**
     * 校验路径格式
     * @param arg 文件绝对路径
     */
    private static void validatePath(String arg) {

    }
}
