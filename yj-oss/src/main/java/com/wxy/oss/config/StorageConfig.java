package com.wxy.oss.config;

import com.wxy.oss.adapter.AliYunAdapter;
import com.wxy.oss.adapter.MinioAdapter;
import com.wxy.oss.adapter.StorageAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: YunJuClub
 * @description: 通过nacos配置文件返回对应的oss服务器类型
 * @author: 32115
 * @create: 2024-03-23 14:41
 */
@Configuration
@RefreshScope
public class StorageConfig {

    @Value("${storage.type}")
    private String storageType;

    @Bean
    @RefreshScope
    public StorageAdapter storageAdapter(){
        if ("minio".equals(storageType)){
            return new MinioAdapter();
        }else if ("aliYun".equals(storageType)){
            return new AliYunAdapter();
        }else {
            throw new IllegalArgumentException("未找到对应的文件存储处理器");
        }
    }
}
