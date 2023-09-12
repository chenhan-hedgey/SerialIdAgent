package org.chenhan.serialAgent.domain.agent.model;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @Author: chenhan
 * @Description: 方法描述
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/8 9:57
 **/
@Data
public class MethodDescription {
    Method method;

    Class[] argsType;

}
