package org.tools.fliter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.tools.serialNumber.SerialThreadData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: chenhan
 * @Description: 流水号拦截器
 * @ProjectName: devtools
 * @Date: 2023/8/29 13:51
 */
public class OrderNumInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(OrderNumInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("成功进入拦截器的 preHandle 方法");

        // 1. 获取 header 中的数据
        String headerData = request.getHeader("ChenHan");


        // 2. 存放 Static Local 方案
        //SerialThreadData.serialThreadDataTest.set("benbiainiaini");
        Map<String,String> map = new HashMap<>();
        map.put("chenhan","ChenWanZhou");
        SerialThreadData.threadData.set(map);
        logger.info("设置了threadData为{}",SerialThreadData.threadData.get());

        // 3. 获取调用的方法和控制器的信息
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            logger.info("调用的方法：{}#{}", controllerName, methodName);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //1.执行完毕，更新请求状态
        logger.info("方法执行完毕");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        logger.info("清除ThreadData");
        SerialThreadData.threadData.remove();
    }
}
