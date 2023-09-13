package org.chenhan.serialAgent.infrastructure.druid;
import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 14:00
 **/
import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.chenhan.serialAgent.domain.context.model.po.DatabaseInfo;

import javax.sql.DataSource;

@Data
public class DruidDataSourceFactory extends PooledDataSourceFactory {

    public static DatabaseInfo databaseInfo;

    public DruidDataSourceFactory(){
        this.dataSource = createDataSource();
    }

    public static DataSource createDataSource() {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(databaseInfo.getUrl());
        dataSource.setUsername(databaseInfo.getUsername());
        dataSource.setPassword(databaseInfo.getPassword());
        // 设置数据源的初始大小为5
        dataSource.setInitialSize(5);
        // 设置数据源的最小空闲连接数为5
        dataSource.setMinIdle(5);
        // 设置数据源的最大活动连接数为20
        dataSource.setMaxActive(20);
        // 设置最大等待时间（毫秒），超过这个时间将抛出异常
        dataSource.setMaxWait(60000);
        // 设置两次空闲连接检测的间隔时间（毫秒）
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 设置连接在池中最小空闲时间（毫秒），超过这个时间将被回收
        dataSource.setMinEvictableIdleTimeMillis(300000);
        // 设置用于验证连接是否有效的SQL查询语句
        dataSource.setValidationQuery("SELECT 1");
        // 在连接空闲时是否进行连接有效性检测
        dataSource.setTestWhileIdle(true);
        // 在从连接池中获取连接时是否进行连接有效性检测
        dataSource.setTestOnBorrow(false);
        // 在归还连接给连接池时是否进行连接有效性检测
        dataSource.setTestOnReturn(false);
        // 是否移除长时间未使用的连接
        dataSource.setRemoveAbandoned(true);
        // 设置长时间未使用的连接的超时时间（秒）
        dataSource.setRemoveAbandonedTimeout(1800);
        // 是否记录移除的长时间未使用的连接日志
        dataSource.setLogAbandoned(true);
        return dataSource;
    }

}
