package org.chenhan.serialAgent.context.service.config;

import org.chenhan.serialAgent.context.model.po.AgentConfig;
import org.chenhan.serialAgent.context.model.po.DatabaseInfo;
import org.chenhan.serialAgent.exception.LoaderException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 14:08
 */
public class SysConfigTest {

    @Test
    public void getSingleton() {
        SysConfig singleton = SysConfig.getSingleton();
        Assert.assertNotEquals(null,singleton);
    }

    @Test
    public void getInitialConfigs() {
        SysConfig singleton = SysConfig.getSingleton();

        try {
            Map<String, String> map = singleton.getInitialConfigs(
                    "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties",
                    false);
            // 读取验证
            Assert.assertEquals("0", map.get("agent.state"));
            Assert.assertEquals("XXX.XXX.XXX.class#method(args)", map.get("agent.interceptMethod"));
            Assert.assertEquals("XXX.XXX.XXX.class#method(args)", map.get("agent.rebaseMethod"));
            Assert.assertEquals("XXX.XXX.class#field", map.get("agent.infoObject"));
            Assert.assertEquals("jdbc:mysql://localhost:3306/mydb", map.get("database.url"));
            Assert.assertEquals("myuser", map.get("database.username"));
            Assert.assertEquals("mypassword", map.get("database.password"));
            Assert.assertEquals("XXX.XXX.class", map.get("database.driverClass"));
        } catch (LoaderException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void load() {
        String path = "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties";
        SysConfig singleton = SysConfig.getSingleton();

        try {
            Map<String, String> map = singleton.getInitialConfigs(
                    path,
                    false);
            AgentConfig agentConfig = singleton.getAgentConfig();
            DatabaseInfo databaseInfo = singleton.getDatabaseInfo();
            Integer stateNo = singleton.getStateNo();
            //验证
            Assert.assertEquals(0,agentConfig.getStateCode().intValue());
            Assert.assertEquals("XXX.XXX.XXX.class#method(args)",agentConfig.getInterceptMethod());
            Assert.assertEquals("XXX.XXX.XXX.class#method(args)",agentConfig.getRebaseMethod());
            Assert.assertEquals("XXX.XXX.class#field",agentConfig.getInfoObject());
            Assert.assertEquals(databaseInfo.getUrl(),"jdbc:mysql://localhost:3306/mydb");
            Assert.assertEquals(databaseInfo.getUsername(),"myuser");
            Assert.assertEquals(databaseInfo.getPassword(),"mypassword");
            Assert.assertEquals(databaseInfo.getDriverClass(),"XXX.XXX.class");

        } catch (LoaderException e) {
            e.printStackTrace();
        }
    }
}