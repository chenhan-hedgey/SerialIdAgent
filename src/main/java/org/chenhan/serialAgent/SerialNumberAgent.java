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
import java.util.HashSet;
import java.util.Set;

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
     *
     * @param arg             传递的命令行参数，传递配置文件地址
     * @param instrumentation Instrumentation对象，用于在Java应用程序启动时转换字节码
     */
    public static void premain(String arg, Instrumentation instrumentation) {
        try {
            // 1. 获取配置文件
            String path = doGetConfigPath(arg);
            logger.info("正在加载配置文件: {}", path);

            // 2. 加载配置文件
            doLoadConfig(path);

            // 3. 加载日志配置
            doLogConfig();

            // 4. 执行agent
            buildAgent(instrumentation);

            // 5. 完成agent的安装
            logger.info("流水号Agent结束执行...");
        } catch (Throwable e) {
            // 获取异常堆栈跟踪
            String errorMessage = e.getMessage();
            // 获取异常堆栈跟踪
            String stackTrace = ExceptionUtils.getStackTrace(e);
            logger.error("安装agent出错 - 错误信息: {}\n堆栈跟踪:\n{}", errorMessage, stackTrace);
        }
    }

    /**
     * 构建并安装Agent，根据配置文件中的信息拦截指定类的方法。
     *
     * @param instrumentation Instrumentation对象，用于在Java应用程序启动时转换字节码
     * @throws AgentException 如果Agent配置或转换失败，则抛出异常
     */
    private static void buildAgent(Instrumentation instrumentation) throws AgentException {
        // 获取SysConfig的单例实例，用于访问配置信息
        SysConfig sysConfig = SysConfig.getSingleton();

        // 创建ParseElementMatcherGenerator以生成ElementMatcher
        ParseElementMatcherGenerator parseElementMatcherGenerator = new ParseElementMatcherGenerator();

        // 创建MethodTransformerGenerator以生成AgentBuilder.Transformer
        MethodTransformerGenerator methodTransformerGenerator = new MethodTransformerGenerator(parseElementMatcherGenerator);

        // 获取代理拦截方法的配置
        String interceptMethod = sysConfig.getAgentConfig().getInterceptMethod();
        String[] stringArray = AgentConfig.getStringArray(interceptMethod);

        // 设置方法信息，用于指定要拦截的方法
        methodTransformerGenerator.setMethodInfo(stringArray[1]);

        // 生成AgentBuilder.Transformer，用于方法转换
        AgentBuilder.Transformer transformer = methodTransformerGenerator.builderTransformer();

        // 获取要拦截的类名
        String className = sysConfig.getAgentConfig().getInterceptClassString();

        // 生成ElementMatcher，匹配要拦截的类
        ElementMatcher typeMatcher = parseElementMatcherGenerator.build(className);

        // 创建AgentGenerator用于构建和安装Agent
        AgentGenerator baseGenerator = BaseGenerator.builder()
                .agentBuilder(new AgentBuilder.Default())
                .listener(new SerialListener())
                .elementMatcher(typeMatcher)
                .transformer(transformer)
                .build();

        // 安装Agent
        baseGenerator.installAgent(instrumentation);
    }


    /**
     * 做日志配置
     *
     * @throws AgentException 如果日志配置失败，则抛出异常
     */
    private static void doLogConfig() throws AgentException {
        // 获取SysConfig的单例实例，用于访问配置信息
        SysConfig sysConfig = SysConfig.getSingleton();
        // 从配置中获取代理日志存放的路径
        String logDir = sysConfig.getInitialConfigs().get("agent.logDir");
        // 打印日志存放路径信息
        logger.info("日志存放的路径: {}", logDir);
        // 设置系统属性，以便ConfoundLogback.xml中可以找到日志文件的存放路径SERIAL_LOG_HOME
        System.setProperty("SERIAL_LOG_HOME", logDir);
        // 配置Logback，获取使用指定的日志配置文件路径
        LogbackConfig.configureLogback(sysConfig.getInitialConfigs().get("agent.logConfigPath"));

    }

    /**
     * 加载配置文件
     *
     * @param path 配置文件的路径
     * @throws AgentException 如果配置文件加载失败，则抛出异常
     */
    private static void doLoadConfig(String path) throws AgentException {
        SysConfig sysConfig = SysConfig.getSingleton();
        sysConfig.loadConfig(path, false);
    }

    /**
     * 探寻配置文件的路径
     *
     * @param arg 传入的配置文件的路径
     * @return 返回配置文件的路径，如果没有找到抛出异常
     * @throws AgentException 如果未找到配置文件，则抛出异常
     */
    private static String doGetConfigPath(String arg) throws AgentException {
        // 1. 判断文件的路径arg是否存在
        if (!StringUtils.isBlank(arg)) {
            logger.info("传入的配置文件路径为: {}", arg);
            return arg;
        }
        logger.info("没有传入的配置文件路径");

        // 2. 如果不存在，探寻默认的路径USER_ROOT_DIR/serial/下是否存在sample-config.properties文件
        String defaultPath = USER_ROOT_DIR + "/serial/" + CONFIG_FILE_SUFFIX;
        if (fileExists(defaultPath)) {
            logger.info("采用默认的配置文件路径为: {}", defaultPath);
            return defaultPath;
        }
        logger.info("默认的配置文件路径 {} 不存在,遍历目录获取配置文件中...", defaultPath);

        // 3. 如果2不满足，在USER_ROOT_DIR下遍历其下所有目录搜索是否有sample-config.properties文件
        Set<String> exclusionDirs = new HashSet<>();
        String configPath = searchForFile(USER_ROOT_DIR, CONFIG_FILE_SUFFIX,exclusionDirs);
        if (configPath != null) {
            logger.info("遍历目录获取配置得到: {}", configPath);
            return configPath;
        }

        logger.info("未找到配置文件");

        // 4. 如果3不满足，抛出异常，提示信息未找到配置文件
        throw new AgentException("未找到配置文件");
    }
}
