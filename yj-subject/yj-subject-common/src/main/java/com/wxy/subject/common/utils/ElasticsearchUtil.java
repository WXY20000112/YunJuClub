package com.wxy.subject.common.utils;

import co.elastic.clients.elasticsearch._types.SearchType;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.wxy.subject.common.es.ElasticsearchIndexInfo;
import com.wxy.subject.common.es.ElasticsearchRestClientApi;
import com.wxy.subject.common.es.ElasticsearchSearchRequest;
import com.wxy.subject.common.es.ElasticsearchSourceData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;


/**
 * @program: YunJuClub-Flex
 * @description: Elasticsearch工具类
 * @author: 32115
 * @create: 2024-05-27 15:46
 */
@SuppressWarnings("rawtypes")
@Slf4j
public class ElasticsearchUtil {

    /**
     * @author: 32115
     * @description: 创建索引
     * @date: 2024/5/27
     * @param: indexInfo
     * @return: Boolean
     */
    public static Boolean createIndex(ElasticsearchIndexInfo indexInfo) {
        try {
            return ElasticsearchRestClientApi.getClient(indexInfo.getClusterName())
                    .indices().create(c -> c
                            .index(indexInfo.getIndexName())
                    ).acknowledged();
        } catch (IOException e) {
            log.error("创建索引失败:createIndex.ElasticsearchIndexInfo:{}", indexInfo, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 删除索引
     * @date: 2024/5/27
     * @param: indexInfo
     * @return: Boolean
     */
    public static Boolean deleteIndex(ElasticsearchIndexInfo indexInfo) {
        try {
            return ElasticsearchRestClientApi.getClient(indexInfo.getClusterName())
                    .indices().delete(c -> c
                            .index(indexInfo.getIndexName())
                    ).acknowledged();
        } catch (IOException e) {
            log.error("删除索引失败:deleteIndex.ElasticsearchIndexInfo:{}", indexInfo, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 索引是否存在
     * @date: 2024/5/27
     * @param: indexInfo
     * @return: Boolean
     */
    public static Boolean indexExists(ElasticsearchIndexInfo indexInfo) {
        try {
            return ElasticsearchRestClientApi.getClient(indexInfo.getClusterName())
                    .indices().exists(c -> c
                            .index(indexInfo.getIndexName())
                    ).value();
        } catch (IOException e) {
            log.error("查询索引是否存在失败:indexExists.ElasticsearchIndexInfo:{}", indexInfo, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 获取索引
     * @date: 2024/5/27
     * @param: indexInfo
     * @return: GetIndexResponse
     */
    public static GetIndexResponse getIndex(ElasticsearchIndexInfo indexInfo) {
        try {
            return ElasticsearchRestClientApi.getClient(indexInfo.getClusterName())
                    .indices().get(c -> c
                            .index(indexInfo.getIndexName())
                    );
        } catch (IOException e) {
            log.error("查询索引失败:getIndex.ElasticsearchIndexInfo:{}", indexInfo, e);
            return null;
        }
    }

    /**
     * @author: 32115
     * @description: 插入文档
     * @date: 2024/5/27
     * @param: indexInfo
     * @param: sourceData
     * @return: Boolean
     */
    public static Boolean insertDoc(ElasticsearchIndexInfo indexInfo, ElasticsearchSourceData sourceData) {
        try {
            CreateResponse createResponse = ElasticsearchRestClientApi.getClient(indexInfo.getClusterName())
                    .create(c -> c
                            .index(indexInfo.getIndexName())
                            .id(sourceData.getDocId())
                            .document(sourceData.getData())
                    );
            return createResponse.result().toString().equals("Created");
        } catch (IOException e) {
            log.error("插入文档失败:insertDoc.ElasticsearchIndexInfo:{},ElasticsearchSourceData:{}",
                    indexInfo, sourceData, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 修改文档
     * @date: 2024/5/27
     * @param: indexInfo
     * @param: sourceData
     * @param: clazz
     * @return: Boolean
     */
    public static Boolean updateDoc(ElasticsearchIndexInfo indexInfo, ElasticsearchSourceData sourceData) {
        try {
            return ElasticsearchRestClientApi.getClient(indexInfo.getClusterName()).update(c -> c
                            .index(indexInfo.getIndexName())
                            .id(sourceData.getDocId())
                            .doc(sourceData.getData()),
                            Map.class
            ).result().toString().equals("Updated");
        } catch (IOException e) {
            log.error("修改文档失败:updateDoc.ElasticsearchIndexInfo:{}," +
                            "ElasticsearchSourceData:{}", indexInfo, sourceData, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 根据文档id删除
     * @date: 2024/5/27
     * @param: indexInfo
     * @param: sourceData
     * @return: Boolean
     */
    public static Boolean deleteDoc(ElasticsearchIndexInfo indexInfo, ElasticsearchSourceData sourceData) {
        try {
            DeleteResponse deleteResponse = ElasticsearchRestClientApi
                    .getClient(indexInfo.getClusterName())
                    .delete(c -> c
                            .index(indexInfo.getIndexName())
                            .id(sourceData.getDocId())
                    );
            return deleteResponse.result().toString().equals("Deleted");
        } catch (IOException e) {
            log.error("删除文档失败:deleteDoc.ElasticsearchIndexInfo:{},ElasticsearchSourceData:{}",
                    indexInfo, sourceData, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 根据文档id查询
     * @date: 2024/5/27
     * @param: indexInfo
     * @param: sourceData
     * @param: clazz
     * @return: GetResponse<?>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getDoc(ElasticsearchIndexInfo indexInfo, ElasticsearchSourceData sourceData){
        try {
            return ElasticsearchRestClientApi
                    .getClient(indexInfo.getClusterName())
                    .get(g -> g
                            .index(indexInfo.getIndexName())
                            .id(sourceData.getDocId()),
                            Map.class
                    ).source();
        } catch (IOException e) {
            log.error("查询文档失败:getDoc.ElasticsearchIndexInfo:{}", indexInfo, e);
            return null;
        }
    }

    /**
     * @author: 32115
     * @description: 批量插入文档
     * @date: 2024/5/27
     * @param: indexInfo
     * @param: sourceDataList
     * @return: Boolean
     */
    public static Boolean bulkInsertDoc(ElasticsearchIndexInfo indexInfo, List<ElasticsearchSourceData> sourceDataList) {
        try {
            List<BulkOperation> bulkOperationList = new ArrayList<>();
            for (ElasticsearchSourceData sourceData : sourceDataList) {
                bulkOperationList.add(new BulkOperation.Builder().create(up -> up
                        .document(sourceData.getData())
                        .id(sourceData.getDocId())
                        .index(indexInfo.getIndexName())
                ).build());
            }
            BulkResponse response = ElasticsearchRestClientApi.getClient(indexInfo.getClusterName())
                    .bulk(c -> c
                            .index(indexInfo.getIndexName())
                            .operations(bulkOperationList)
                    );
            return response.errors();
        } catch (IOException e){
            log.error("批量插入文档失败:bulkInsertDoc.ElasticsearchIndexInfo:{},ElasticsearchSourceDataList:{}",
                    indexInfo, sourceDataList, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 批量修改文档
     * @date: 2024/5/27
     * @param: indexInfo
     * @param: sourceDataList
     * @return: Boolean
     */
    public static Boolean bulkUpdateDoc(ElasticsearchIndexInfo indexInfo, List<ElasticsearchSourceData> sourceDataList) {
        try {
            List<BulkOperation> bulkOperationList = new ArrayList<>();
            for (ElasticsearchSourceData sourceData : sourceDataList) {
                bulkOperationList.add(new BulkOperation.Builder().update(up -> up
                        .action(a -> a
                                .doc(sourceData.getData())
                        )
                        .id(sourceData.getDocId())
                        .index(indexInfo.getIndexName())
                ).build());
            }
            BulkResponse response = ElasticsearchRestClientApi.getClient(indexInfo.getClusterName())
                    .bulk(c -> c
                            .index(indexInfo.getIndexName())
                            .operations(bulkOperationList)
                    );
            return response.errors();
        } catch (IOException e){
            log.error("批量更新文档失败:bulkUpdateDoc.ElasticsearchIndexInfo:{},ElasticsearchSourceDataList:{}",
                    indexInfo, sourceDataList, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 删除所有文档
     * @date: 2024/5/27
     * @param: indexInfo
     * @return: Boolean
     */
    public static Boolean deleteAllDoc(ElasticsearchIndexInfo indexInfo) {
        try {
            // 执行删除操作
            DeleteByQueryResponse deleteByQuery = ElasticsearchRestClientApi
                    .getClient(indexInfo.getClusterName())
                    .deleteByQuery(d -> d
                            .index(indexInfo.getIndexName())
                            .query(q -> q
                                    .matchAll(m -> m)
                            )
                    );
            System.out.println("所有文档已从 " + indexInfo.getIndexName() + " 索引中删除");
            if (deleteByQuery.batches() == null) return false;
            return deleteByQuery.batches().intValue() == 1;
        } catch (Exception e) {
            log.error("批量更新文档失败:bulkUpdateDoc.ElasticsearchIndexInfo:{}",
                    indexInfo, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 判断文档是否存在
     * @date: 2024/5/27
     * @param: indexInfo
     * @param: sourceData
     * @return: Boolean
     */
    public static Boolean existDoc(ElasticsearchIndexInfo indexInfo, ElasticsearchSourceData sourceData) {
        try {
            return ElasticsearchRestClientApi.getClient(indexInfo.getClusterName()).exists(c -> c
                    .index(indexInfo.getIndexName())
                    .id(sourceData.getDocId())
            ).value();
        } catch (IOException e) {
            log.error("查询文档是否存在失败:existDoc.ElasticsearchIndexInfo:{}" +
                    ",ElasticsearchSourceData:{}", indexInfo, sourceData, e);
            return false;
        }
    }

    /**
     * @author: 32115
     * @description: 根据id查询文档并返回指定字段
     * @date: 2024/5/27
     * @param: indexInfo
     * @param: sourceData
     * @param: clazz
     * @param: fields
     * @return: SearchResponse<?>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getDoc(ElasticsearchIndexInfo indexInfo,
                                      ElasticsearchSourceData sourceData,
                                      String[] fields){
        try {
            return ElasticsearchRestClientApi.getClient(indexInfo.getClusterName())
                    .get(g -> g
                            .index(indexInfo.getIndexName())
                            .id(sourceData.getDocId())
                            .source(s -> s
                                    .fields(Arrays.asList(fields))
                            ),
                            Map.class
                    ).source();
        } catch (IOException e) {
            log.error("查询文档失败:getDoc.ElasticsearchIndexInfo:{}", indexInfo, e);
            return null;
        }
    }

    /**
     * @author: 32115
     * @description: 分页查询并对关键字进行高亮展示
     * @date: 2024/5/30
     * @param: indexInfo
     * @param: searchRequest
     * @return: SearchResponse<Map>
     */
    public static SearchResponse<Map> searchDoc(ElasticsearchIndexInfo indexInfo, ElasticsearchSearchRequest searchRequest) {
        try {
            // 构建搜索请求对象，设置索引名称
            SearchRequest.Builder searchReq = new SearchRequest.Builder();
            searchReq.index(indexInfo.getIndexName());
            // 配置要返回的源字段，只包含请求中指定的字段
            SourceConfig.Builder sourceConfig = new SourceConfig.Builder();
            sourceConfig.filter(f -> f
                    .includes(Arrays.asList(searchRequest.getFields()))
            );
            // 是否强制刷新配置数据。如果为true，则强制从源端刷新数据；如果为false，则根据配置的刷新策略决定是否刷新。
            sourceConfig.fetch(true);
            // 设置bool组合查询条件
            searchReq.query(q -> q
                    .bool(searchRequest.getBoolQueryBuilder())
            );
            // 应用源配置到搜索请求
            searchReq.source(sourceConfig.build());
            // 如果存在高亮设置，则添加到搜索请求中
            if (Objects.nonNull(searchRequest.getHighlightBuilder())){
                searchReq.highlight(searchRequest.getHighlightBuilder());
            }
            // 如果指定了排序字段，则设置排序条件
            if (StringUtils.isNotBlank(searchRequest.getSortName())){
                searchReq.sort(s -> s
                        .field(f -> f
                                .field(searchRequest.getSortName())
                                .order(SortOrder.Desc)
                        )
                );
            }
            // 设置搜索类型为QueryThenFetch
            searchReq.searchType(SearchType.QueryThenFetch);
            // 如果需要滚动搜索，则设置滚动参数
            if (searchRequest.getNeedScroll()){
                searchReq.scroll(s -> s
                        .time(String.valueOf(searchRequest.getScrollTime()))
                );
            }
            // 执行搜索请求并返回结果
            return ElasticsearchRestClientApi.getClient(indexInfo.getClusterName())
                    .search(searchReq.build(), Map.class);
        } catch (IOException e) {
            log.error("查询文档失败:searchDoc.ElasticsearchIndexInfo:{}", indexInfo, e);
            return null;
        }
    }
}
