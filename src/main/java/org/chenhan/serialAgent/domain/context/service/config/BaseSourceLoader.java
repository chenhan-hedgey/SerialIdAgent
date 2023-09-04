package org.chenhan.serialAgent.domain.context.service.config;

import org.chenhan.serialAgent.exception.AgentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/4 13:02
 */
public abstract class BaseSourceLoader implements SourceLoader {
    private static final Logger logger = LoggerFactory.getLogger(BaseSourceLoader.class);
    /**
     * 配置文件校验器
     */
    private ValidateSource validateSource;
    public BaseSourceLoader() {
        this(new ValidateSource.Default());
    }

    public BaseSourceLoader(ValidateSource validateSource) {
        this.validateSource = validateSource;
    }

    /**
     * 从指定的完整路径加载资源，并将其内容作为键值对形式的 Map 返回,并且完成校验
     *
     * @param fullPath 资源的完整路径，需要被加载。
     * @return 包含加载资源内容的键值对形式的 Map。
     * @throws AgentException 如果加载资源时出现异常。
     */
    @Override
    public Map<String, String> loadWithCheck(String fullPath) throws AgentException {
        try{
            Map<String, String> map = load(fullPath);
            Integer state = validateSource.validate(map);
            return map;
        }catch (AgentException e){
            logger.info("配置加载失败,文件路径:{}",fullPath);
            throw  e;
        }
    }
}
