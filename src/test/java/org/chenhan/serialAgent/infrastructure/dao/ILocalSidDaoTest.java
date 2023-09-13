package org.chenhan.serialAgent.infrastructure.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.chenhan.serialAgent.domain.context.service.config.SysConfig;
import org.chenhan.serialAgent.exception.AgentException;
import org.chenhan.serialAgent.infrastructure.druid.DruidDataSourceFactory;
import org.chenhan.serialAgent.infrastructure.po.LocalSid;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 16:48
 **/
public class ILocalSidDaoTest {

    SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws IOException, AgentException {
        String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";
        SysConfig sysConfig = SysConfig.getSingleton();
        sysConfig.loadConfig(path,false);
        DruidDataSourceFactory.databaseInfo = sysConfig.getDatabaseInfo();
        String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void insert() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        ILocalSidDao mapper = sqlSession.getMapper(ILocalSidDao.class);
        LocalSid localSid = new LocalSid();
        localSid.setId(null);
        localSid.setGlobalId(10016);
        localSid.setParentId(12138);
        localSid.setFlowId(123425);
        localSid.setStatus(0);  // 0表示成功，你可以根据需要设置不同的状态
        localSid.setIp("192.168.1.101");
        localSid.setApiName("chenhan");
        localSid.setCreateTime(new Date());  // 设置创建时间
        localSid.setUpdateTime(new Date());  // 设置修改时间
        System.out.println(localSid);
        System.out.println(mapper.insert(localSid));
        System.out.println(localSid);
        sqlSession.commit();
    }

    @Test
    public void updateState() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
        ILocalSidDao mapper = sqlSession.getMapper(ILocalSidDao.class);
        LocalSid localSid = new LocalSid();
        localSid.setId(10000);
        localSid.setResult("调用的结果");
        localSid.setCodeValue(1222);
        localSid.setStatus(1223);
        mapper.updateState(localSid);
        sqlSession.commit();
    }
}