package org.chenhan.serialAgent.infrastructure;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.chenhan.serialAgent.infrastructure.dao.UserDao;
import org.chenhan.serialAgent.infrastructure.po.User;

import java.io.IOException;
import java.io.Reader;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 11:25
 **/
public class MyBatisExample {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 获取 Mapper 接口
            UserDao userMapper = session.getMapper(UserDao.class);

            // 执行查询
            User user = userMapper.getUser(1);
            System.out.println(userMapper.getUserList(2));
            System.out.println(user);
        }
    }
}
