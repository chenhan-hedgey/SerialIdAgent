package org.chenhan.serialAgent.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenhan
 * @Description: 局部流水号
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 15:01
 **/
import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalSid implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    private Long id;


    /**
     * 全局流水号，从10000开始，至int的最大值
     */
    private Integer globalId;

    /**
     * 表示本次调用的父级请求id
     */
    private Integer parentId;

    /**
     * 调用方的IP地址
     */
    private String ip;

    /**
     * 公司内部的规范的流水号
     */
    private Integer flowId;

    /**
     * json字符串
     */
    private String result;

    /**
     * 调用的情况展示,0成功、1异常、2失败
     */
    private Integer status;

    /**
     * 外部系统返回的结果码
     */
    private Integer codeValue;

    /**
     * 被调用的哪一个接口
     */
    private String apiName;

    /**
     * 创建时间，默认为当前时间
     */
    private Date createTime;

    /**
     * 修改时间，默认为当前时间，但在更新时自动更新为当前时间
     */
    private Date updateTime;
}
