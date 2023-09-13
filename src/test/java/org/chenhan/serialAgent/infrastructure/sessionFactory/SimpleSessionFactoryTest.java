package org.chenhan.serialAgent.infrastructure.sessionFactory;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.chenhan.serialAgent.domain.context.service.config.SysConfig;
import org.chenhan.serialAgent.exception.AgentException;
import org.chenhan.serialAgent.infrastructure.dao.IGlobalSidDao;
import org.chenhan.serialAgent.infrastructure.druid.DruidDataSourceFactory;
import org.chenhan.serialAgent.infrastructure.po.GlobalSid;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 17:30
 **/
public class SimpleSessionFactoryTest {
    @Test
    public void testWithoutConfig() throws IOException {

        SimpleSessionFactory singleton = SimpleSessionFactory.singleton();
        singleton.build("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = singleton.getSqlSessionFactory();
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            IGlobalSidDao mapper = sqlSession.getMapper(IGlobalSidDao.class);
            GlobalSid globalSid = new GlobalSid();
            globalSid.setId(null);
            globalSid.setCaller("测试SimpleFactory");
            globalSid.setFlowId(123425);
            globalSid.setStatus(0);  // 0表示成功，你可以根据需要设置不同的状态
            globalSid.setIp("192.168.1.101");
            globalSid.setTxCode("TestCode");
            globalSid.setCreateTime(new Date());  // 设置创建时间
            globalSid.setUpdateTime(new Date());  // 设置修改时间
            mapper.insert(globalSid);
            sqlSession.commit();
        }catch (Exception e){

        }
    }

    @Test
    public void testWithConfig() throws IOException, AgentException {
        String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";
        SysConfig sysConfig = SysConfig.getSingleton();
        sysConfig.loadConfig(path,false);
        DruidDataSourceFactory.databaseInfo = sysConfig.getDatabaseInfo();
        SimpleSessionFactory singleton = SimpleSessionFactory.singleton();
        singleton.build("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = singleton.getSqlSessionFactory();
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            IGlobalSidDao mapper = sqlSession.getMapper(IGlobalSidDao.class);
            GlobalSid globalSid = new GlobalSid();
            globalSid.setId(10015);
            globalSid.setStatus(100);
            globalSid.setResultCode(1003);
            mapper.updateState(globalSid);
            sqlSession.commit();
        }
    }
}