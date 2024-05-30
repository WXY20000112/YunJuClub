package com.wxy.subject.common.es;

import lombok.Data;

import java.util.Map;

/**
 * @program: YunJuClub-Flex
 * @description: 用于表示Elasticsearch中的源数据 作用是用来封装Elasticsearch中的文档数据，方便进行操作和处理。
 * @author: 32115
 * @create: 2024-05-27 12:04
 */
@Data
public class ElasticsearchSourceData {

    /**
     * 表示文档的唯一标识符，类型为String
     */
    private String docId;

    /**
     * 表示文档的具体数据，以Map形式存储
     */
    private Map<String, Object> data;
}
