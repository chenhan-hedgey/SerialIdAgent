package org.chenhan.serialAgent.context.model.po;

import lombok.Data;

/**
 * @Author: chenhan
 * @Description: 数据库相关信息
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 11:47
 */
@Data
public class DatabaseInfo {
    /**
     * 数据库的URL。
     */
    private String url;

    /**
     * 数据库的驱动类。
     */
    private String driverClass;

    /**
     * 访问数据库的用户名。
     */
    private String username;

    /**
     * 访问数据库的密码。
     */
    private String password;
}
