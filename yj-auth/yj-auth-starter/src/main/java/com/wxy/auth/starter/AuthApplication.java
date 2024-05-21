package com.wxy.auth.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @program: YunJuClub-Flex
 * @description: 权限验证服务启动类
 * @author: 32115
 * @create: 2024-05-17 13:47
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.wxy")
@MapperScan("com.wxy.auth.**.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        // 屏蔽nacos日志
        System.setProperty("nacos.logging.default.config.enabled", "false");
        // 启动 Spring Boot 应用
        SpringApplication.run(AuthApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  云桔社区权限验证服务启动成功   ლ(´ڡ`ლ)ﾞ  \n" + """
                __     ___    _ _   _      _ _    _  _____ _     _    _ ____ \s
                \\ \\   / / |  | | \\ | |    | | |  | |/ ____| |   | |  | |  _ \\\s
                 \\ \\_/ /| |  | |  \\| |    | | |  | | |    | |   | |  | | |_) |
                  \\   / | |  | | . ` |_   | | |  | | |    | |   | |  | |  _ <\s
                   | |  | |__| | |\\  | |__| | |__| | |____| |___| |__| | |_) |\s
                   |_|   \\____/|_| \\_|\\____/ \\____/ \\_____|______\\____/|____/""");
    }
}