package org.chenhan.serialAgent.domain.parse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenhan
 * @Description: token
 * @ProjectName: devtools
 * @Date: 2023/9/5 9:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String type;
    private String value;
}