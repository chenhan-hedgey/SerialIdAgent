package org.chenhan.serialAgent.domain.parse.model.arg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenhan
 * @Description: 原始的参数类
 * @ProjectName: devtools
 * @Date: 2023/9/4 21:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OriginArgument implements Argument{
    /**
     * class名
     */
   private String type;
    /**
     * json value,用以序列化
     */
    private String value;

    /**
     * 获取原始的type值
     *
     * @return
     */
    @Override
    public String getOriginType() {
        return getType();
    }

    /**
     * 获取原始的json值
     *
     * @return
     */
    @Override
    public String getOriginValue() {
        return getValue();
    }
}
