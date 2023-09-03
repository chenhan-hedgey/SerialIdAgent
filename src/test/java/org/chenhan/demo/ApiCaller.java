package org.chenhan.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @Author: chenhan
 * @Description: 拦截的类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 17:39
 */

public class ApiCaller {
    private static final Logger logger = LoggerFactory.getLogger(ApiCaller.class);
    /**
     * 基本调用
     * @param params
     * @return
     */
    public static String call(String[] params){
        logger.info("param = {}", Arrays.toString(params));
        return "模拟API调用{String[]}";
    }

    /**
     * 全面调用
     * @param params
     * @param anotherParam
     * @return
     */
    public static String call(String[] params,String[] anotherParam){
        logger.info("param = {},anotherParam = {}", Arrays.toString(params),Arrays.toString(anotherParam));
        return "模拟API调用{String[],String[]}";
    }
}
