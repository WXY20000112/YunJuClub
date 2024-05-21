package com.wxy.oss.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: YunJuClub
 * @description: Minio配置
 * @author: 32115
 * @create: 2024-03-23 10:12
 */
@Configuration
public class MinioConfig {

    // minio服务器的url
    @Value("${minio.minioUrl}")
    private String minioUrl;

    // minio的用户名
    @Value("${minio.minioUser}")
    private String minioUser;

    // minio的密码
    @Value("${minio.minioPassword}")
    private String minioPassword;

    /**
     * @author: 32115
     * @description: 获取minio客户端
     * @date: 2024/3/23
     * @return: MinioClient
     */
    @Bean
    public MinioClient getMinioClient(){
        return MinioClient.builder().endpoint(minioUrl).credentials(minioUser, minioPassword).build();
    }
}
