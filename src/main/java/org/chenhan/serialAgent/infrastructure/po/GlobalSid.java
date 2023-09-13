package org.chenhan.serialAgent.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: chenhan
 * @Description: 全局流水号
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 14:59
 **/import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSid implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 全局流水号，从10000开始，自增长
     */
    private Integer id;

    /**
     * 调用方
     */
    private String caller;

    /**
     * 规范流水号，非空
     */
    private Integer flowId;

    /**
     * 状态，0表示成功、1表示异常、2表示失败
     */
    private Integer status;

    /**
     * 调用的结果码
     */
    private Integer resultCode;

    /**
     * 调用方的IP地址
     */
    private String ip;

    /**
     * 交易码
     */
    private String txCode;

    /**
     * 创建时间，默认为当前时间
     */
    private Date createTime;

    /**
     * 修改时间，默认为当前时间，但在更新时自动更新为当前时间
     */
    private Date updateTime;
}
