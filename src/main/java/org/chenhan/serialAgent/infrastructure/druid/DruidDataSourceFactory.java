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
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

import javax.sql.DataSource;

public class DruidDataSourceFactory extends PooledDataSourceFactory {
    public DruidDataSourceFactory(){
        this.dataSource = createDataSource();
    }

    public static DataSource createDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://47.120.15.193:3306/serial");
        dataSource.setUsername("root");
        dataSource.setPassword("786505");

        // 配置连接池参数
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        dataSource.setLogAbandoned(true);

        return dataSource;
    }
}
