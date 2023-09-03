package org.tools.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tools.controller.vo.TestVO;
import org.tools.service.TestService;

/**
 * @Author: chenhan
 * @Description: 用来测试agent的接口
 * @ProjectName: devtools
 * @Date: 2023/8/29 11:37
 */
@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    TestService testService;

    @RequestMapping(method = RequestMethod.POST)
    public String test(@RequestBody TestVO testVO){
        logger.info("进入 test controller，参数为{}",testVO.toString());
        testService.test();
        return "请求成功";
    }

    /**
     * 测试jmeter的接口
     * @return 成功信息
     */
    @RequestMapping(method = RequestMethod.GET)
    public String jmeterTest(){
        return "请求成功";
    }
}
