package com.wxy.practice.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: YunJuClub-Flex
 * @description: 从nacos配置中心读取套卷下各题目类型出题的比例
 * @author: 32115
 * @create: 2024-06-04 15:29
 */
@Data
@Component
@ConfigurationProperties(prefix = "practice.count")
public class PracticeConfig {

    /**
     * 单选题数量
     */
    private Integer radioSubjectCount;

    /**
     * 多选题数量
     */
    private Integer multipleSubjectCount;

    /**
     * 判断题数量
     */
    private Integer judgeSubjectCount;

    /**
     * 简答题数量
     */
    private Integer briefSubjectCount;
}
