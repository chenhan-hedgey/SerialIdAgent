package org.chenhan.serialAgent.domain.context;

import ch.qos.logback.core.boolex.Matcher;
import lombok.Data;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.chenhan.serialAgent.domain.context.model.po.AgentConfig;
import org.chenhan.serialAgent.domain.context.service.config.SysConfig;
import org.chenhan.serialAgent.domain.context.service.parse.SimpleMethodParser;
import org.chenhan.serialAgent.domain.support.ReflectionCache;
import org.chenhan.serialAgent.exception.AgentException;
import org.chenhan.serialAgent.util.ReflectionUtils;
import org.chenhan.serialAgent.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: chenhan
 * @Description: agent上下文类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 10:53
 */
@Data
public class AgentContext {


    private static AgentContext agentContext;

    public AgentContext() {

    }


    public static AgentContext singleton(){
        if (agentContext==null){
            synchronized (AgentContext.class){
                if (agentContext==null) {
                    agentContext = new AgentContext();
                }
            }
        }
        return agentContext;
    }


    private static final Logger logger = LoggerFactory.getLogger(AgentContext.class);

    /**
     * 待拦截的类文件
     */
    String originClassName;
    /**
     * 待拦截的方法匹配器
     */
    ElementMatcher.Junction methodMatcher;
    /**
     * 重定向的类
     */
    Class targetClass;
    /**
     * 重定向的方法
     */
    Method targetMethod;
    /**
     * 数据字段,是一个Map<String,String>
     *     不要在这里获取
     */
    Class dataClass;
    /**
     * 字段的名字
     */
    String fieldName;
    /**
     * 系统配置
     */
    SysConfig sysConfig;
/**
 * 配置文件路径
 */
    private String fullPath;

    public void fullPathAndLoad(String fullPath) throws AgentException {
        SysConfig singleton = SysConfig.getSingleton();
        singleton.loadConfig(fullPath,false);
        this.sysConfig = singleton;
    }
    /**
     *
     * @param fullPath
     * @throws AgentException
     */
    public AgentContext(String fullPath) throws AgentException {
        this.fullPath = fullPath;
        SysConfig singleton = SysConfig.getSingleton();
        singleton.loadConfig(fullPath,false);
        this.sysConfig = singleton;
    }
    public AgentContext (SysConfig config){
        this.sysConfig = config;
    }

    public void loadContext() throws AgentException {
        // 分离
        setOriginClassName(StringUtils.classNameFormString(sysConfig.getAgentConfig().getInterceptMethod()));
        String interceptMethod = sysConfig.getAgentConfig().getInterceptMethod();
        String methodInfo = (AgentConfig.getStringArray(interceptMethod))[1];
        setMethodMatcher(new SimpleMethodParser(methodInfo).parse());
        String tClassName = StringUtils.classNameFormString(sysConfig.getAgentConfig().getRebaseMethod());
        Class tClazz = null;
        try {
            tClazz = ReflectionCache.loadClass(tClassName);
        } catch (ClassNotFoundException e) {
            logger.info("没有找到重定向的类，类型为:{}",tClassName);
            throw new AgentException("没有找到待重定向的类",e);
        }
        setTargetClass(tClazz);

        String tMethodName = StringUtils.methodNameFormString(sysConfig.getAgentConfig().getRebaseMethod());
        String[] argsClassName = StringUtils.argsFormString(sysConfig.getAgentConfig().getRebaseMethod());
        Class[] argClasses = null;
        try {
            argClasses = ReflectionCache.getArgClassArray(argsClassName);
        } catch (ClassNotFoundException e) {
            logger.info("拦截方法的参数类型加载失败,失败参数:{}",argsClassName);
            throw new AgentException("拦截方法的参数类型加载失败",e);
        }
        targetMethod = ReflectionCache.loadMethod(tClazz,tMethodName,argClasses);
        String infoObjectName = sysConfig.getAgentConfig().getInfoObject();
        String[] classAndString = StringUtils.getClassAndFiledFormString(infoObjectName);
        try {
            dataClass = ReflectionCache.loadClass(classAndString[0]);
        } catch (ClassNotFoundException e) {
            logger.info("没有找到数据字段，字段信息：{}",infoObjectName);
            throw new AgentException("没有找到数据字段",e);
        }
        fieldName = classAndString[1];

    }

}
