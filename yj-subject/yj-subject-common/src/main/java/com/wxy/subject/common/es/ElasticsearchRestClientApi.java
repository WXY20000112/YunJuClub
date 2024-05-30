package com.wxy.subject.common.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: YunJuClub-Flex
 * @description: 作用是根据配置文件动态地建立和管理多个Elasticsearch集群的连接，并提供一个易于使用的客户端接口
 * @author: 32115
 * @create: 2024-05-27 12:09
 */
@Component
@Slf4j
public class ElasticsearchRestClientApi {

    /**
     * 用于存储Elasticsearch客户端的映射。
     */
    private static final Map<String, ElasticsearchClient> clientMap = new HashMap<>();

    /**
     * Elasticsearch配置属性，用于获取集群配置信息。
     */
    @Resource
    private ElasticsearchConfigProperties elasticsearchConfigProperties;

    /**
     * @author: 32115
     * @description: 初始化函数，用于在应用启动时配置和初始化Elasticsearch客户端
     * @date: 2024/5/27
     * @return: void
     */
    @PostConstruct
    public void initialize() {
        // 获取集群信息列表，并为每个集群初始化Elasticsearch客户端
        List<ElasticsearchClusterInfo> clusterInfoList =
                elasticsearchConfigProperties.getClusterInfoList();
        for (ElasticsearchClusterInfo elasticsearchClusterInfo : clusterInfoList) {
            // 记录初始化日志，包括集群名称和节点信息
            log.info("initialize.config.name:{}, node:{}",
                    elasticsearchClusterInfo.getName(), elasticsearchClusterInfo.getNodes());
            // 获取对应集群的客户端实例
            ElasticsearchClient client = getElasticsearchClient(elasticsearchClusterInfo);
            // 将集群名称和客户端实例保存到映射中
            clientMap.put(elasticsearchClusterInfo.getName(), client);
        }
        log.info("initialize.clientMap:{}", clientMap);
    }

    /**
     * @author: 32115
     * @description: 根据Elasticsearch集群信息获取Elasticsearch客户端。
     * @date: 2024/5/27
     * @param: elasticsearchClusterInfo 包含Elasticsearch集群节点地址信息的对象
     * @return: ElasticsearchClient Elasticsearch客户端实例。
     */
    private ElasticsearchClient getElasticsearchClient(ElasticsearchClusterInfo elasticsearchClusterInfo) {
        // 将集群节点地址字符串分割成单个地址，并转换为HttpHost列表
        String[] nodeAddresses = elasticsearchClusterInfo.getNodes().split(",");
        List<HttpHost> httpHostList = new ArrayList<>(nodeAddresses.length);
        for (String nodeAddress : nodeAddresses) {
            // 将集群节点地址字符串分割成ip地址和端口号
            String[] hostInfo = nodeAddress.split(":");
            // 如果地址包含主机和端口信息，则创建HttpHost实例并添加到列表中
            if (hostInfo.length == 2){
                HttpHost httpHost = new HttpHost(hostInfo[0], NumberUtils.toInt(hostInfo[1]));
                httpHostList.add(httpHost);
            }
        }
        // 将HttpHost列表转换为数组
        HttpHost[] httpHostArray = new HttpHost[httpHostList.size()];
        httpHostList.toArray(httpHostArray);
        // 使用RestClient构建器创建RestClient实例
        RestClient restClientBuilder = RestClient.builder(httpHostArray).build();
        // 使用RestClient和JSON处理器创建Elasticsearch传输层实例
        ElasticsearchTransport elasticsearchTransport =
                new RestClientTransport(restClientBuilder, new JacksonJsonpMapper());
        // 返回基于传输层的Elasticsearch客户端实例
        return new ElasticsearchClient(elasticsearchTransport);
    }

    /**
     * @author: 32115
     * @description: 对外提供获取Elasticsearch客户端的方法。
     * @date: 2024/5/27
     * @param: clusterName
     * @return: ElasticsearchClient
     */
    public static ElasticsearchClient getClient(String clusterName) {
        return clientMap.get(clusterName);
    }
}
