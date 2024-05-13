package com.wxy.subject.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: YunJuClub-Flex
 * @description: 刷题模块启动类
 * @author: 32115
 * @create: 2024-05-13 11:05
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.wxy")
@MapperScan("com.wxy.subject.**.mapper")
public class SubjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubjectApplication.class, args);
    }
}
