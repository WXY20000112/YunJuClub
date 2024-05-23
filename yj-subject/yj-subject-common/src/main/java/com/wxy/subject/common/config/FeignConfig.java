package com.wxy.subject.common.config;

import com.wxy.subject.common.interceptor.FeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: YunJuClub-Flex
 * @description: Feign配置
 * @author: 32115
 * @create: 2024-05-23 19:49
 */
@Configuration
public class FeignConfig {

    // 配置使用自定义拦截器
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignInterceptor();
    }
}
