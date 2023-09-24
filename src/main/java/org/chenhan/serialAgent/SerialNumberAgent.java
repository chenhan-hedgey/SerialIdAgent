package org.chenhan.serialAgent;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.chenhan.serialAgent.domain.agent.service.builder.SerialListener;
import org.chenhan.serialAgent.domain.agent.service.entry.AgentGenerator;
import org.chenhan.serialAgent.domain.agent.service.entry.BaseGenerator;
import org.chenhan.serialAgent.domain.agent.service.matcher.impl.ParseElementMatcherGenerator;
import org.chenhan.serialAgent.domain.agent.service.transfomer.MethodTransformerGenerator;
import org.chenhan.serialAgent.domain.context.model.po.AgentConfig;
import org.chenhan.serialAgent.domain.context.service.config.SysConfig;
import org.chenhan.serialAgent.domain.support.LogbackConfig;
import org.chenhan.serialAgent.exception.AgentException;
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
            String userDir = System.getProperty("user.dir");
            String path = userDir + "/src/main/resources/serial.chenhan.agent/sample-configs.properties";
            //String path = userDir + "\\src\\main\\resources\\serial.chenhan.agent\\sample-configs.properties";
            path = arg==null?path:arg;
            if (arg == null) {
                logger.info("传入的配置文件路径为空，使用默认的配置路径：{}",path);
            }
            logger.info("流水号Agent开始执行,配置路径为:{}...",path);

            // 1.加载配置文件，初始化配置
            if (StringUtils.isBlank(path)) {
                logger.error("配置文件路径为空，请检查路径");
                return;
            }
            SysConfig sysConfig = SysConfig.getSingleton();
            sysConfig.loadConfig(path,false);
            String logDir = sysConfig.getInitialConfigs().get("agent.logDir");
            logger.info("日志存放的路径:{}",logDir);
            System.setProperty("SERIAL_LOG_HOME",logDir);
            LogbackConfig.configureLogback(sysConfig.getInitialConfigs().get("agent.logConfigPath"));
            logger.info("测试2");
            ParseElementMatcherGenerator parseElementMatcherGenerator = new ParseElementMatcherGenerator();
            MethodTransformerGenerator methodTransformerGenerator = new MethodTransformerGenerator(parseElementMatcherGenerator);

            String interceptMethod = sysConfig.getAgentConfig().getInterceptMethod();
            String[] stringArray = AgentConfig.getStringArray(interceptMethod);
            methodTransformerGenerator.setMethodInfo(stringArray[1]);
            AgentBuilder.Transformer transformer = methodTransformerGenerator.builderTransformer();

            // class name
            String className = sysConfig.getAgentConfig().getInterceptClassString();
            ElementMatcher typeMatcher = parseElementMatcherGenerator.build(className);

            AgentGenerator baseGenerator = BaseGenerator.builder()
                    .agentBuilder(new AgentBuilder.Default())
                    .listener(new SerialListener())
                    .elementMatcher(typeMatcher)
                    .transformer(transformer)
                    .build();
            baseGenerator.installAgent(instrumentation);

            // 3.安装agent
            logger.info("流水号Agent结束执行...");
        }
        catch ( Throwable e) {
            // 获取异常堆栈跟踪
            String errorMessage = e.getMessage();
            // 获取异常堆栈跟踪
            String stackTrace = ExceptionUtils.getStackTrace(e);
            logger.error("安装agent出错 - 错误信息: {}\n堆栈跟踪:\n{}", errorMessage, stackTrace);
        }

    }



}
