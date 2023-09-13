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
