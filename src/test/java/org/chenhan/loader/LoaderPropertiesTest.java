package org.chenhan.loader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: chenhan
 * @Description: 加载properties文件
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 10:34
 */
public class LoaderPropertiesTest {
    private static final Logger logger = LoggerFactory.getLogger(LoaderPropertiesTest.class);

    @Test
    public  void main() {
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\src\\main\\resources\\sample-configs.properties")) {
            properties.load(inputStream);
            // 获取属性值
            String mock = properties.getProperty("agent.mock");
            String interceptMethod = properties.getProperty("agent.interceptMethod");
            String rebaseMethod = properties.getProperty("agent.rebaseMethod");
            String infoObject = properties.getProperty("agent.infoObject");

            String dbUrl = properties.getProperty("database.url");
            String dbUsername = properties.getProperty("database.username");
            String dbPassword = properties.getProperty("database.password");

            logger.info("Mock: {}", mock);
            logger.info("Intercept Method: {}", interceptMethod);
            logger.info("Rebase Method: {}", rebaseMethod);
            logger.info("Info Object: {}", infoObject);

            logger.info("Database URL: {}", dbUrl);
            logger.info("Database Username: {}", dbUsername);
            logger.info("Database Password: {}", dbPassword);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
