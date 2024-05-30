package com.wxy.subject.common.es;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: 用于读取Spring Boot应用的配置文件中以"elasticsearch.cluster"为前缀的配置属性，并将其绑定到类的属性上
 * @author: 32115
 * @create: 2024-05-27 11:04
 */
@Data
@Component
@ConfigurationProperties(prefix = "elasticsearch.cluster")
public class ElasticsearchConfigProperties {

    /**
     * 用于存储Elasticsearch集群的信息
     */
    private List<ElasticsearchClusterInfo> clusterInfoList = new ArrayList<>();
}
