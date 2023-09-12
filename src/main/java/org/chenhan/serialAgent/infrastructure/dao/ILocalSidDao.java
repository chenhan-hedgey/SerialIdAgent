package org.chenhan.serialAgent.infrastructure.dao;

import org.chenhan.serialAgent.infrastructure.po.LocalSid;

/**
 * @Author: chenhan
 * @Description: 用于操作局部流水号的数据访问对象（DAO）接口
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 15:07
 */
public interface ILocalSidDao {

    /**
     * 插入局部流水号记录
     *
     * @param localSid 要插入的局部流水号对象
     */
    int insert(LocalSid localSid);

    /**
     * 更新局部流水号的状态
     *
     * @param localSid 包含更新状态信息的局部流水号对象
     * @return 影响的行数（通常是1表示成功，0表示失败）
     */
    int updateState(LocalSid localSid);
}
