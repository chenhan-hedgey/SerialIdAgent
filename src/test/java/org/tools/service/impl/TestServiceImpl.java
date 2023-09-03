package org.tools.service.impl;

import org.springframework.stereotype.Service;
import org.tools.mockAPI.ApiCaller;
import org.tools.service.TestService;

/**
 * @Author: chenhan
 * @Description: 测试的Service
 * @ProjectName: devtools
 * @Date: 2023/8/29 15:18
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public void test() {
        // 拦截本方法并且取出serialThreadDataTest的数据，调用为call(String[],String[])
        ApiCaller.call(new String[]{"陈含","王永瑶","吴浪浪"});
    }
}
