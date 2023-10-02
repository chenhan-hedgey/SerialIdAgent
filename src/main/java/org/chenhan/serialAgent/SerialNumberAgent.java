package org.chenhan.serialAgent;

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

import java.io.File;
import java.lang.instrument.Instrumentation;

import static org.chenhan.serialAgent.util.FileUtils.*;

/**
 * @Author: chenhan
 * @Description: premain类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 10:09
 */
public class SerialNumberAgent {
    private static final Logger logger = LoggerFactory.getLogger(SerialNumberAgent.class);

    /**
     * 默认的后缀
     */
    private static final String CONFIG_FILE_SUFFIX = "sample-configs.properties";
    /**
     * 用户路径
     */
    private static final String USER_ROOT_DIR = System.getProperty("user.dir");

    /**
     * premain方法，执行agent的配置逻辑
     * @param arg 传递的命令行参数，传递配置文件地址
     * @param instrumentation Instrumentation对象，用于在Java应用程序启动时转换字节码
     */
    public static void premain(String arg, Instrumentation instrumentation)  {
        try {

            // 1. 获取配置文件
            String path = doGetConfigPath(arg);
            // 2. 加载配置文件
            doLoadConfig(path);
            // 3. 加载日志配置
            doLogConfig();
            // 4. 执行agent


            //String path = USER_ROOT_DIR + "/src/main/resources/serial.chenhan.agent/sample-configs.properties";
            //path = arg==null?path:arg;
            //if (arg == null) {
            //    logger.info("传入的配置文件路径为空，使用默认的配置路径：{}",path);
            //}
            //logger.info("流水号Agent开始执行,配置路径为:{}...",path);
            //
            //// 1.加载配置文件，初始化配置
            //if (StringUtils.isBlank(path)) {
            //    logger.error("配置文件路径为空，请检查路径");
            //    return;
            //}
            SysConfig sysConfig = SysConfig.getSingleton();
            //sysConfig.loadConfig(path,false);
            String logDir = sysConfig.getInitialConfigs().get("agent.logDir");
            logger.info("日志存放的路径:{}",logDir);
            System.setProperty("SERIAL_LOG_HOME",logDir);
            LogbackConfig.configureLogback(sysConfig.getInitialConfigs().get("agent.logConfigPath"));
            // 安装agent
            buildAgent(instrumentation);

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

    private static void buildAgent(Instrumentation instrumentation) throws AgentException {
        SysConfig sysConfig = SysConfig.getSingleton();
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

    }

    /**
     * 做日志配置
     */
    private static void doLogConfig() throws AgentException {
        SysConfig sysConfig = SysConfig.getSingleton();
        String logDir = sysConfig.getInitialConfigs().get("agent.logDir");
        logger.info("日志存放的路径:{}",logDir);
        System.setProperty("SERIAL_LOG_HOME",logDir);
        LogbackConfig.configureLogback(sysConfig.getInitialConfigs().get("agent.logConfigPath"));
    }

    private static void doLoadConfig(String path) throws AgentException {
        SysConfig sysConfig = SysConfig.getSingleton();
        sysConfig.loadConfig(path,false);
    }



    /**
     * 探寻配置文件的路径
     * @param arg 传入的配置文件的路径
     * @return 返回配置文件的路径，如果没有找到抛出异常
     * @throws AgentException 如果未找到配置文件，则抛出异常
     */
    private static String doGetConfigPath(String arg) throws AgentException {
        // 1. 判断文件的路径arg是否存在
        if (!StringUtils.isBlank(arg)) {
            return arg;
        }

        // 2. 如果不存在，探寻默认的路径USER_ROOT_DIR/serial/下是否存在sample-config.properties文件
        String defaultPath = USER_ROOT_DIR + "/serial/" + CONFIG_FILE_SUFFIX;
        if (fileExists(defaultPath)) {
            return defaultPath;
        }

        // 3. 如果2不满足，在USER_ROOT_DIR下遍历其下所有目录搜索是否有sample-config.properties文件
        String configPath = searchForFile(USER_ROOT_DIR,CONFIG_FILE_SUFFIX);
        if (configPath != null) {
            return configPath;
        }

        // 4. 如果3不满足，抛出异常，提示信息未找到配置文件
        throw new AgentException("未找到配置文件");
    }



}
