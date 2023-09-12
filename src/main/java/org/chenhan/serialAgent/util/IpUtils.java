package org.chenhan.serialAgent.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: chenhan
 * @Description: IpUtils工具
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 17:02
 **/
public class IpUtils {
    /**
     * 获取当前主机的IP地址
     *
     * @return 当前主机的IP地址字符串，或者null（如果发生异常）
     */
    public static String getCurrentIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }
}