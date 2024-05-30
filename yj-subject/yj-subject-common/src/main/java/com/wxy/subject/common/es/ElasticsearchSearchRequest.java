package com.wxy.subject.common.es;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import lombok.Data;

/**
 * @program: YunJuClub-Flex
 * @description: 该Java类是一个用于构建Elasticsearch搜索请求的模型类 它包含了构建Elasticsearch搜索请求所需的各种属性
 * @author: 32115
 * @create: 2024-05-27 11:49
 */
@Data
public class ElasticsearchSearchRequest {

    /**
     * 查询条件
     */
    private BoolQuery boolQueryBuilder;

    /**
     * 查询字段
     */
    private String[] fields;

    /**
     * 页数
     */
    private Integer from;

    /**
     * 数据条数
     */
    private Integer size;

    /**
     * 是否需要快照
     */
    private Boolean needScroll;

    /**
     * 快照时间
     */
    private Long scrollTime;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 排序类型
     */
    private SortOrder sortOrder;

    /**
     * 高亮builder
     */
    private Highlight highlightBuilder;

}
