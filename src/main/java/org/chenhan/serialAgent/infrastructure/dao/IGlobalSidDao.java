package org.chenhan.serialAgent.infrastructure.dao;

import org.chenhan.serialAgent.infrastructure.po.GlobalSid;

/**
 * @Author: chenhan
 * @Description: 用于操作全局流水号的数据访问对象（DAO）接口
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 15:07
 */
public interface IGlobalSidDao {

    /**
     * 插入全局流水号记录
     *
     * @param globalSid 要插入的全局流水号对象
     */
    int insert(GlobalSid globalSid);

    /**
     * 更新全局流水号的状态
     *
     * @param globalSid 包含更新状态信息的全局流水号对象
     * @return 影响的行数（通常是1表示成功，0表示失败）
     */
    int updateState(GlobalSid globalSid);
}
