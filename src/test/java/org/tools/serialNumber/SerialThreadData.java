package org.tools.serialNumber;

import java.util.Map;

/**
 * @Author: chenhan
 * @Description: 线程独享的Thread Local变量，用于反射调用
 * @ProjectName: devtools
 * @Date: 2023/8/29 14:38
 */
public class SerialThreadData {
    /**
     * 测试数据-用于agent获取
     */
    public static ThreadLocal<Map<String,String>>  threadData = new ThreadLocal<>();


    /**
     * 使用普通变量命名
     */
}
