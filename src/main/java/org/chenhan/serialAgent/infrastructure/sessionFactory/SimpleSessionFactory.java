package org.chenhan.serialAgent.infrastructure.sessionFactory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @Author: chenhan
 * @Description: mybatis的sqlSessionFactory
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 17:18
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSessionFactory {

    private static SimpleSessionFactory sessionFactory;

    public static SimpleSessionFactory singleton(){
        if (sessionFactory==null){
            synchronized (SimpleSessionFactory.class){
                if (sessionFactory==null) {
                    sessionFactory = new SimpleSessionFactory();
                }
            }
        }
        return sessionFactory;
    }



    private SqlSessionFactory sqlSessionFactory;

    /**
     * 加载SqlSessionFactory仓库
     * @param resource mybatis配置文件路径，相对路径
     * @throws IOException
     */
    public void build(String resource) throws IOException {
        Reader reader = Resources.getResourceAsReader(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }


}
