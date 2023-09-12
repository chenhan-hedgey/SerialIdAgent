package org.chenhan.serialAgent.infrastructure.sessionFactory;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.chenhan.serialAgent.infrastructure.dao.IGlobalSidDao;
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
    public void test() throws IOException {
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
}