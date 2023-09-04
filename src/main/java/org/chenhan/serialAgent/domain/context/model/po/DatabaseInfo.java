package org.chenhan.serialAgent.domain.context.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
/**
 * @Author: chenhan
 * @Description: 数据库相关信息
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 11:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     * 从提供的键值对映射中读取数据库信息，并根据需要更新字段的值。
     * 如果键值对映射为空或缺少某些字段，将保留原有字段值。
     *
     * @param map 包含数据库信息的键值对映射。
     */
    public void readMap(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }

        url = StringUtils.defaultIfBlank(map.get("database.url"), url);
        driverClass = StringUtils.defaultIfBlank(map.get("database.driverClass"), driverClass);
        username = StringUtils.defaultIfBlank(map.get("database.username"), username);
        password = StringUtils.defaultIfBlank(map.get("database.password"), password);
    }
}
