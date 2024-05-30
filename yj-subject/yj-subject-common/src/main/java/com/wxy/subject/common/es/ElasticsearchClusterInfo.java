package com.wxy.subject.common.es;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: Elasticsearch集群的信息 用于存储Elasticsearch集群的相关信息
 * @author: 32115
 * @create: 2024-05-27 10:50
 */
@Data
public class ElasticsearchClusterInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 集群节点名称
     */
    private String name;

    /**
     * 集群节点地址
     */
    private String nodes;
}
