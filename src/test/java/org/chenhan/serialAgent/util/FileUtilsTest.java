package org.chenhan.serialAgent.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @Author: ChenHan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/10/2 13:15
 **/
public class FileUtilsTest {

    @Test
    public void searchForFile() {
        String root = System.getProperty("user.dir");
        System.out.println(FileUtils.searchForFile(root, "sample-configs.properties"));
        System.out.println(FileUtils.searchForFile(root, "mybatis-config.xml"));
        System.out.println(FileUtils.searchForFile(root, "readme.md"));
        System.out.println(FileUtils.searchForFile(root, "test"));

    }

    @Test
    public void testSearchForFile() {
        String root = System.getProperty("user.dir");
        Set<String> exceptDirs = new HashSet<>(10);
        System.out.println(FileUtils.searchForFile(root, "mybatis-config.xml",exceptDirs));
        exceptDirs.add("src");
        System.out.println(FileUtils.searchForFile(root, "mybatis-config.xml",exceptDirs));
    }

    /**
     * 测试文件存在的方法
     */
    @Test
    public void fileExists() {
        // 指定存在的文件路径
        String[] existingFilePaths = {
                "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\test\\fileutil\\fileIsExist.txt",
                "C:/Users/Administrator/Desktop/readCode/SerialNumberAgent/test/fileutil/fileIsExist.txt",
        };

        // 遍历每个存在的文件路径并验证文件是否存在
        for (String filePath : existingFilePaths) {
            assertTrue( "文件应存在：" + filePath,FileUtils.fileExists(filePath));
        }
        // 指定不存在的文件路径
        String[] nonExistingFilePaths = {
                "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\test\\fileutil\\fileIsExist\\filenotexist.txt",
                "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\test\\fileutil\\fileIsExist\\filenoexist",
                "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\test\\fileutil\\fileIsExist\\filenoexist\\ssdss",
                "C:/Users/Administrator/Desktop/readCode/SerialNumberAgent/test/fileutil/fileIsExist",
                "C:\\Users\\Administrator\\Desktop\\readCode\\SerialNumberAgent\\test\\fileutil\\fileIsExist"
        };

        // 遍历每个不存在的文件路径并验证文件是否不存在
        for (String filePath : nonExistingFilePaths) {
            assertFalse( "文件不应存在：" + filePath,FileUtils.fileExists(filePath));
        }
    }
}