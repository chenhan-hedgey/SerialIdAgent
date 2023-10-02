package org.chenhan.serialAgent.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: ChenHan
 * @Description: 文件工具类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/28 16:17
 **/
public class FileUtils {

    /**
     * 在指定目录下递归搜索文件。
     *
     * @param directory        搜索的目录
     * @param fileName         要搜索的文件名
     * @return 如果找到指定的文件，返回文件的绝对路径；如果未找到，返回null。
     */
    public static String searchForFile(String directory, String fileName) {
        return searchForFile(directory, fileName, new HashSet<>());
    }

    /**
     * 在指定目录下递归搜索文件，可以排除指定的目录。
     *
     * @param directory          搜索的目录
     * @param fileName           要搜索的文件名
     * @param exceptDirectories  要排除的目录集合
     * @return 如果找到指定的文件，返回文件的绝对路径；如果未找到，返回null。
     */
    public static String searchForFile(String directory, String fileName, Set<String> exceptDirectories) {
        // 在指定目录下递归搜索配置文件的逻辑
        File dir = new File(directory);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                // 如果file是一个目录，并且没有被排除
                if (file.isDirectory() && (!exceptDirectories.contains(file.getName()))) {
                    // 递归搜索子目录
                    String path = searchForFile(file.getAbsolutePath(), fileName);
                    if (path != null) {
                        return path;
                    }
                } else if (file.getName().equals(fileName)) {
                    // 如果找到文件名为fileName的文件，返回其路径
                    return file.getAbsolutePath();
                }
            }
        }
        return null;
    }

    /**
     * 检查文件是否存在。
     *
     * @param path 要检查的文件路径
     * @return 如果文件存在且是一个普通文件，返回true；否则返回false。
     */
    public static boolean fileExists(String path) {
        // 检查文件是否存在的逻辑
        File file = new File(path);
        return file.exists() && file.isFile();
    }
}
