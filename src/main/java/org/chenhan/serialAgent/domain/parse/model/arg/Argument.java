package org.chenhan.serialAgent.domain.parse.model.arg;

/**
 * @Author: chenhan
 * @Description: 参数接口
 * @ProjectName: devtools
 * @Date: 2023/9/5 8:37
 */
public interface Argument {

    /**
     * 获取原始的type值
     * @return
     */
    String getOriginType();

    /**
     * 获取原始的json值
     * @return
     */
    String getOriginValue();
}
