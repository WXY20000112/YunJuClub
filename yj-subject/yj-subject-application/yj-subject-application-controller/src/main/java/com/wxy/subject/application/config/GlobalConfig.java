package com.wxy.subject.application.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @program: YunJuClub
 * @description: 全局空值处理 当返回结果为null是将这些字段自动隐藏
 * @author: 32115
 * @create: 2024-03-22 11:14
 */
@Configuration
public class GlobalConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureMessageConverters(@Nonnull List<HttpMessageConverter<?>> converters) {
        // 将父类的规则仍然保留继承下来
        super.configureMessageConverters(converters);
        // 添加一个新的规则
        converters.add(mappingJackson2HttpMessageConverter());
    }

    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        ObjectMapper objectMapper = new ObjectMapper();
        // 添加规则不显示null字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
