package org.tools.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tools.fliter.OrderNumInterceptor;

/**
 * @Author: chenhan
 * @Description: Web配置
 * @ProjectName: devtools
 * @Date: 2023/8/29 14:01
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册流水号拦截器
        registry.addInterceptor(new OrderNumInterceptor());
    }
}