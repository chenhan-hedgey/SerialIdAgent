package org.chenhan.serialAgent.domain.context.service.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.chenhan.serialAgent.domain.context.model.po.AgentConfig;
import org.chenhan.serialAgent.domain.context.model.po.DatabaseInfo;

import org.chenhan.serialAgent.domain.context.service.config.impl.PropertiesLoader;
import org.chenhan.serialAgent.exception.AgentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: chenhan
 * @Description: 项目配置
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 10:54
 */
@Getter
@Setter
public class SysConfig {
    private static final Logger logger = LoggerFactory.getLogger(SysConfig.class);

    /**
     * 配置对象
     */
    private static SysConfig sysConfig;
    /**
     * 配置路径
     */
    private static String configPath;

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

    /**
     * 设置配置路径
     * @param fullPath 设置路径
     */
    public static void setConfigPath(String fullPath){
        configPath = fullPath;
    }


    /**
     * 获取路径
     * @return
     */
    public static String getConfigPath(){
        return configPath;
    }
    public SysConfig(){
        this(new PropertiesLoader());
    }
    public SysConfig(SourceLoader sourceLoader) {
        this.stateNo = 0;
        // 已有配置路径
        if (!StringUtils.isBlank(configPath)){
            stateNo = stateNo|2;
        }
        this.stateNo|=4;
        this.sourceLoader = sourceLoader;
    }

    /**
     * SysConfig的状态：
     * 0-未实例化&未有配置路径&未加载配置
     * 1-未实例化&未有配置路径&已加载配置
     * 2-未实例化&已有配置路径&未加载配置
     * 3-未实例化&已有配置路径&已加载配置
     * 4-已实例化&未有配置路径&未加载配置
     * 5-已实例化&未有配置路径&已加载配置
     * 6-已实例化&已有配置路径&未加载配置
     * 7-已实例化&已有配置路径&已加载配置
     */
    private Integer stateNo;
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
     * 数据库信息
     */
    private DatabaseInfo databaseInfo;


    /**
     * 加载配置文件,获取初始配置,如果已有配置,判断是否需要重新加载
     * @param fullPath 全路径
     * @param useExistingConfig
     */
    public void loadConfig(String fullPath,Boolean useExistingConfig) throws AgentException {
        logger.info("是否已有配置:{}，是否使用已有配置:{}",initialConfigs==null,useExistingConfig);
        if (initialConfigs!=null&&useExistingConfig){
            logger.info("已有的配置,直接使用");
        }
        else{
            logger.info("正在加载配置路径为{}",fullPath);
            load(fullPath);
            refresh();
        }
        logger.info("已有的配置信息为:{}",initialConfigs);
    }

    /**
     * 加载配置文件,获取初始配置,如果已有配置,判断是否需要重新加载
     * @param fullPath 全路径
     * @param useExistingConfig 是否使用已有的config
     */
    public Map<String,String> getInitialConfigs(String fullPath,Boolean useExistingConfig) throws AgentException {
        loadConfig(fullPath,useExistingConfig);
        return initialConfigs;
    }

    /**
     * 获取对应参数
     * @return 获取原始配置数据（Map）
     * @throws AgentException
     */
    public Map<String,String> getInitialConfigs() throws AgentException {
        return initialConfigs;
    }
    /**
     * 加载配置文件
     * @param fullPath
     * @throws AgentException
     */
    public  void load(String fullPath) throws AgentException {
        if (!StringUtils.equals(fullPath, configPath)) {
            setConfigPath(fullPath);
            stateNo|=2;
        }
        initialConfigs = sourceLoader.load(fullPath);
        //未加载配置
        if ((stateNo&1)==0) {
            agentConfig = new AgentConfig();
            databaseInfo = new DatabaseInfo();
            stateNo = 1;
        }
        agentConfig.readMap(initialConfigs);
        databaseInfo.readMap(initialConfigs);
        stateNo|=1;
    }

    /**
     * 根据initialConfig更新agentConfig中所有的配置字段
     */
    private  void refresh() {
        logger.info("配置文件已改变，正在更新字段...");
        agentConfig.refresh(initialConfigs);
    }

}
