package org.chenhan.serialAgent.domain.context.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.chenhan.serialAgent.domain.context.AgentContext;
import org.chenhan.serialAgent.exception.AgentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: chenhan
 * @Description: agent配置:用于agent的build等配置
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 11:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentConfig {
    private static final Logger logger = LoggerFactory.getLogger(AgentConfig.class);
    /**
     * 操作模式：
     * 0-真实，1-mock
     */
    private volatile Integer stateCode;
    /**
     * 执行状态
     * 0-执行，1-停止
     */
    private volatile Integer executeCode;

    /**
     * 待拦截的信息（静态）
     */
    private String interceptMethod;
    /**
     * 重定向的方法
     */
    private String rebaseMethod;

    /**
     * 线程信息字段
     */
    private String infoObject;

    public String getInterceptClassString() throws AgentException {
        String[] arr = getStringArray(interceptMethod);
        return arr[1];
    }

    public static String[] getStringArray(String classAndMethod) throws AgentException {
        if (classAndMethod == null || classAndMethod.length() == 0) {
            logger.info("方法格式不正确,except:XXX.XXX.class#method(XXX),实际:{}", classAndMethod);
            throw new AgentException("方法字符串为null");
        }
        String[] split = classAndMethod.split("#");
        if (split==null||split.length!=2) {
            logger.info("方法格式不正确,except:XXX.XXX.class#method(XXX),实际:{}", classAndMethod);
            throw new AgentException("方法格式不正确");
        }
        return split;
    }


    /**
     * 从提供的键值对映射中读取代理配置信息，并根据需要更新字段的值。
     * 如果键值对映射为空或缺少某些字段，将保留原有字段值。
     *
     * @param map 包含代理配置信息的键值对映射。
     */
    public void readMap(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        // 读取并更新操作模式（状态码）
        if ((!StringUtils.isBlank(map.get("agent.state")))&&StringUtils.isNumeric(map.get("agent.state"))) {
            Integer tmp = Integer.parseInt(map.get("agent.state"));
            stateCode = tmp;
        }
        // 读取并更新待拦截的信息（静态方法）
        interceptMethod = StringUtils.defaultIfBlank(map.get("agent.interceptMethod"), interceptMethod);
        // 读取并更新重定向的方法
        rebaseMethod = StringUtils.defaultIfBlank(map.get("agent.rebaseMethod"), rebaseMethod);
        // 读取并更新线程信息字段
        infoObject = StringUtils.defaultIfBlank(map.get("agent.infoObject"), infoObject);
    }

    /***
     * 刷新字段
     * @param map 参数Map
     */
    public void refresh(final Map<String,String> map){
        readMap(map);
    }


}
