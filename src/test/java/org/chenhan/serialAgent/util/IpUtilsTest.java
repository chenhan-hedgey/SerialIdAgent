package org.chenhan.serialAgent.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: chenhan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 17:04
 **/
public class IpUtilsTest {

    @Test
    public void getCurrentIpAddress() {
        System.out.println(IpUtils.getCurrentIpAddress());
    }
}