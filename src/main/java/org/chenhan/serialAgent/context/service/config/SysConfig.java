package org.chenhan.serialAgent.context.service.config;

import org.chenhan.serialAgent.context.model.po.AgentConfig;
import org.chenhan.serialAgent.context.service.config.impl.PropertiesLoader;
import org.chenhan.serialAgent.exception.LoaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: chenhan
 * @Description: 项目配置
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 10:54
 */
public class SysConfig {
    private static final Logger logger = LoggerFactory.getLogger(SysConfig.class);

    private static SysConfig sysConfig;

    /***
     * 单例方法
     * @return 单例的SysConfig对象
     */
    public static SysConfig getSingleton(){
        if (sysConfig==null) {
            synchronized (SysConfig.class){
                if (sysConfig==null) {
                    sysConfig = new SysConfig();
                }
            }
        }
        return sysConfig;
    }

    public SysConfig(){
        this(new PropertiesLoader());
    }
    public SysConfig(SourceLoader sourceLoader) {
        this.sourceLoader = sourceLoader;
    }

    /**
     * 配置加载方式，默认properties文件
     */
    private  SourceLoader sourceLoader;
    /**
     * 初始的配置信息
     */
    private  Map<String,String> initialConfigs;

    /**
     * agent的基本信息
     */
    private AgentConfig agentConfig;




    /**
     * 加载配置文件,获取初始配置,如果已有配置,判断是否需要重新加载
     * @param fullPath 全路径
     * @param useExistingConfig
     */
    public Map<String,String> getInitialConfigs(String fullPath,Boolean useExistingConfig) throws LoaderException {
        logger.info("是否已有配置:{}，是否使用已有配置:{}",initialConfigs==null,useExistingConfig);
        if (initialConfigs!=null&&useExistingConfig){
            logger.info("已有的配置,直接使用");
        }
        else{
            logger.info("正在加载配置路径为{}",fullPath);
            load(fullPath);
        }
        logger.info("已有的配置信息为:{}",initialConfigs);
        return initialConfigs;
    }

    /**
     * 加载配置文件
     * @param fullPath
     * @throws LoaderException
     */
    public  void load(String fullPath) throws LoaderException {
        initialConfigs = sourceLoader.load(fullPath);
        refresh();
    }

    /**
     * 根据initialConfig更新agentConfig中所有的配置字段
     */
    private  void refresh() {
        logger.info("配置文件已改变，正在更新字段...");
        agentConfig.refresh(initialConfigs);
    }

}
