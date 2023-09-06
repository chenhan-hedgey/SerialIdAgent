package org.chenhan.serialAgent.domain.parse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenhan
 * @Description: 生成token的帮助实体类
 * @ProjectName: devtools
 * @Date: 2023/9/5 10:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenIndex {
    /**
     * token实例
     */
    private Token token;
    /**
     * 下一次读取的位置
     */
    private Integer nextIndex;
}
