package org.chenhan.serialAgent.infrastructure.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.chenhan.serialAgent.infrastructure.po.GlobalSid;
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
 * @Date: 2023/9/12 15:37
 **/
public class IGlobalSidDaoTest {
    SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws IOException {
        String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void insert() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        IGlobalSidDao mapper = sqlSession.getMapper(IGlobalSidDao.class);
        GlobalSid globalSid = new GlobalSid();
        globalSid.setId(null);
        globalSid.setCaller("YourCaller");
        globalSid.setFlowId(123425);
        globalSid.setStatus(0);  // 0表示成功，你可以根据需要设置不同的状态
        globalSid.setIp("192.168.1.101");
        globalSid.setTxCode("TestCode");
        globalSid.setCreateTime(new Date());  // 设置创建时间
        globalSid.setUpdateTime(new Date());  // 设置修改时间
        System.out.println(globalSid);
        System.out.println(mapper.insert(globalSid));
        System.out.println(globalSid);
        sqlSession.commit();
    }

    @Test
    public void updateState() {
        SqlSession sqlSession = this.sqlSessionFactory.openSession(true);
        IGlobalSidDao mapper = sqlSession.getMapper(IGlobalSidDao.class);
        GlobalSid globalSid = new GlobalSid();
        globalSid.setId(10016);
        globalSid.setCaller("YourCaller");
        globalSid.setFlowId(12345);
        globalSid.setStatus(1111);  // 0表示成功，你可以根据需要设置不同的状态
        globalSid.setResultCode(100);
        globalSid.setIp("192.168.1.101");
        globalSid.setTxCode("TestCode");
        globalSid.setCreateTime(new Date());  // 设置创建时间
        globalSid.setUpdateTime(new Date());  // 设置修改时间
        mapper.updateState(globalSid);
        sqlSession.commit();
    }
}