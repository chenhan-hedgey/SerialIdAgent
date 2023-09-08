package org.chenhan.serialAgent.domain.parse.model;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenhan
 * @Description: 参数类型
 * @ProjectName: devtools
 * @Date: 2023/9/5 10:30
 */
@Getter
public enum TokenType {
    /**
     * 运算符
     */
    OPERATOR(1, "OPERATOR", "&&、||、！"),
    /**
     * 括号
     */
    PARENTHESIS(2, "PARENTHESIS", "左括号和右括号"),
    /**
     * 函数名
     */
    FUNCTION(3, "FUNCTION", "包括函数名称与参数"),
    /**
     * 参数
     */
    //ARGUMENTS(4, "ARGUMENTS", "不进行更细粒度的划分，比如 named(String[].class,int.class),String[].class,int.class 被认为是一个 token"),
    /**
     * 其他类型
     */
    OTHERS(5,"OTHERS","未识别的类型");
    private Integer code;
    private String type;
    private String info;

    //public Integer getCode(){
    //    return code;
    //}

    private static final Map<Integer,TokenType> map = new HashMap<>(5);

    static {
        for (TokenType tokenType : values()) {
            map.put(tokenType.getCode(), tokenType);
        }
    }

    TokenType(Integer code, String type, String info) {
        this.code = code;
        this.type = type;
        this.info = info;
    }

    public static TokenType fromCode(Integer code) {
        return map.get(code);
    }
}