package org.chenhan.serialAgent.infrastructure.dao;

import org.chenhan.serialAgent.infrastructure.po.User;

import java.util.List;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 11:14
 **/
public interface UserDao {
    public User getUser(int id);
    List<User> getUserList(int id);
}
