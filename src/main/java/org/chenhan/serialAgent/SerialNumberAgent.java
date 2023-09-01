package org.chenhan.serialAgent;

import java.lang.instrument.Instrumentation;

/**
 * @Author: chenhan
 * @Description: premain类
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/1 10:09
 */
public class SerialNumberAgent {
    /**
     * premain方法，执行agent的配置逻辑
     * @param arg 传递的命令行参数，传递配置文件地址
     * @param instrumentation Instrumentation对象，用于在Java应用程序启动时转换字节码
     */
    public static void premain(String arg, Instrumentation instrumentation) {
        // 1.加载配置文件，初始化配置
        // 2.配置agent
        // 3.安装agent
    }
}
