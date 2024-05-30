package com.wxy.subject.common.es;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: 用于表示Elasticsearch索引的信息
 * @author: 32115
 * @create: 2024-05-27 11:42
 */
@Data
public class ElasticsearchIndexInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    /**
     * 索引的名称
     */
    private String indexName;

    /**
     * 集群的名称
     */
    private String clusterName;
}
