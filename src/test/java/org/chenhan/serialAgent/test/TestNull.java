package org.chenhan.serialAgent.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ChenHan
 * @Description:
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/22 14:13
 **/
public class TestNull {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("chen","1");
        map.put("vk","null");
        map.put("a",null);
        map.put("v",null);
        map.put("s",null);
        System.out.println(map.containsValue("null"));
        System.out.println(map.containsValue("1"));
        System.out.println(map.containsValue(null));

    }
}
