package com.wxy.subject.infra.es.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.mybatisflex.core.paginate.Page;
import com.wxy.subject.common.constant.SubjectFieldsElasticsearchConstant;
import com.wxy.subject.common.enums.SubjectTypeEnum;
import com.wxy.subject.common.es.ElasticsearchIndexInfo;
import com.wxy.subject.common.es.ElasticsearchSearchRequest;
import com.wxy.subject.common.es.ElasticsearchSourceData;
import com.wxy.subject.common.utils.ElasticsearchUtil;
import com.wxy.subject.infra.es.entity.SubjectInfoElasticsearch;
import com.wxy.subject.infra.es.service.SubjectInfoElasticsearchService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoElasticsearchServiceImpl
 * @author: 32115
 * @create: 2024-05-28 12:39
 */
@SuppressWarnings("rawtypes")
@Service
public class SubjectInfoElasticsearchServiceImpl implements SubjectInfoElasticsearchService {

    /**
     * @author: 32115
     * @description: 分页高亮查询
     * @date: 2024/5/28
     * @param: subjectInfoElasticsearch
     * @return: Page<SubjectInfoElasticsearch>
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Page<SubjectInfoElasticsearch> getSubjectPageList(SubjectInfoElasticsearch subjectInfoEs) {
        Page<SubjectInfoElasticsearch> pageResult = new Page<>();
        // 根据传入的搜索条件创建Elasticsearch的搜索请求
        ElasticsearchSearchRequest esSearchRequest = createEsSearchRequest(subjectInfoEs);
        // 执行Elasticsearch搜索，并获取搜索结果
        SearchResponse<Map> searchResponse =
                ElasticsearchUtil.searchDoc(getElasticsearchIndexInfo(), esSearchRequest);
        List<SubjectInfoElasticsearch> infoElasticsearchList = new ArrayList<>();
        // 如果搜索结果为空，则设置分页结果为无记录
        if (Objects.requireNonNull(searchResponse).hits() == null || searchResponse.hits().hits() == null){
            pageResult.setPageNumber(subjectInfoEs.getPageNo());
            pageResult.setPageSize(subjectInfoEs.getPageSize());
            pageResult.setRecords(infoElasticsearchList);
            pageResult.setTotalPage(0);
            pageResult.setTotalRow(0);
            return pageResult;
        }
        // 处理搜索结果，转换为SubjectInfoElasticsearch对象列表
        List<Hit<Map>> resultMap = searchResponse.hits().hits();
        for (Hit<Map> mapHit : resultMap) {
            SubjectInfoElasticsearch subjectInfoElasticsearch = converterResult(mapHit);
            // 如果搜索结果不为空，则添加到结果列表中
            if (Objects.nonNull(subjectInfoElasticsearch)) {
                infoElasticsearchList.add(subjectInfoElasticsearch);
            }
        }
        // 设置分页结果的页码、每页显示条数和记录列表
        pageResult.setPageNumber(subjectInfoEs.getPageNo());
        pageResult.setPageSize(subjectInfoEs.getPageSize());
        pageResult.setRecords(infoElasticsearchList);
        // 计算总页数，并设置到pageResult中
        long totalPage = Objects.requireNonNull(searchResponse.hits().total()).value() % subjectInfoEs.getPageSize();
        pageResult.setTotalPage(totalPage == 0 ?
                searchResponse.hits().total().value() / subjectInfoEs.getPageSize() :
                (searchResponse.hits().total().value() / subjectInfoEs.getPageSize()) + 1);
        // 设置总行数 也就是数据总条数
        pageResult.setTotalRow(searchResponse.hits().total().value());
        // 返回分页结果
        return pageResult;
    }

    /**
     * @author: 32115
     * @description: 将Elasticsearch搜索结果的Hit对象转换为SubjectInfoElasticsearch对象。
     * @date: 2024/5/30
     * @param: mapHit
     * @return: SubjectInfoElasticsearch
     */
    @SuppressWarnings("unchecked")
    private SubjectInfoElasticsearch converterResult(Hit<Map> mapHit) {
        Map<String, Object> resultMap = mapHit.source();
        //检查结果集是否为空
        if (CollectionUtils.isEmpty(resultMap)){
            return null;
        }
        SubjectInfoElasticsearch subjectInfoElasticsearch = new SubjectInfoElasticsearch();
        // 从结果集中提取并设置主题ID
        subjectInfoElasticsearch.setSubjectId(MapUtils
                .getLong(resultMap, SubjectFieldsElasticsearchConstant.SUBJECT_ID));
        // 从结果集中提取并设置主题名称
        subjectInfoElasticsearch.setSubjectName(MapUtils
                .getString(resultMap, SubjectFieldsElasticsearchConstant.SUBJECT_NAME));
        // 从结果集中提取并设置主题答案
        subjectInfoElasticsearch.setSubjectAnswer(MapUtils
                .getString(resultMap, SubjectFieldsElasticsearchConstant.SUBJECT_ANSWER));
        // 从结果集中提取并设置文档ID
        subjectInfoElasticsearch.setDocId(MapUtils
                .getLong(resultMap, SubjectFieldsElasticsearchConstant.DOC_ID));
        // 从结果集中提取并设置题目类型
        subjectInfoElasticsearch.setSubjectType(MapUtils
                .getInteger(resultMap, SubjectFieldsElasticsearchConstant.SUBJECT_TYPE));
        // 计算并设置题目相关性分数
        subjectInfoElasticsearch.setScore(new BigDecimal(String.valueOf(mapHit.score()))
                .multiply(new BigDecimal("100.00")
                .setScale(2, RoundingMode.HALF_UP)));
        // 处理高亮显示的部分
        Map<String, List<String>> highlight = mapHit.highlight();
        // 处理主题目名称的高亮显示
        List<String> subjectNameFields = highlight
                .get(SubjectFieldsElasticsearchConstant.SUBJECT_NAME);
        for (String subjectNameField : subjectNameFields) {
            subjectInfoElasticsearch.setSubjectName(subjectNameField);
        }
        // 处理题目答案的高亮显示
        List<String> subjectAnswerFields = highlight
                .get(SubjectFieldsElasticsearchConstant.SUBJECT_ANSWER);
        for (String subjectAnswerField : subjectAnswerFields) {
            subjectInfoElasticsearch.setSubjectAnswer(subjectAnswerField);
        }
        // 返回转换后的结果
        return subjectInfoElasticsearch;
    }

    /**
     * @author: 32115
     * @description: 创建Elasticsearch的搜索请求对象。
     * @date: 2024/5/30
     * @param: subjectInfoEs
     * @return: ElasticsearchSearchRequest
     */
    private ElasticsearchSearchRequest createEsSearchRequest(SubjectInfoElasticsearch subjectInfoEs) {
        ElasticsearchSearchRequest esSearchRequest = new ElasticsearchSearchRequest();
        // 构建布尔查询，组合多个搜索条件
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
        // should 条件 满足其一即可  相当于 or
        boolQuery.should(s -> s
                .match(m -> m
                        .field(SubjectFieldsElasticsearchConstant.SUBJECT_NAME)
                        .query(subjectInfoEs.getKeyWord())
                        /*
                         * 提升某个操作的优先级或影响程度。
                         * 在搜索引擎或算法中，boost常用于调整某个项目的相对重要性。
                         * boost 值越大，表示提升的优先级或影响程度越高。此例中为2F。
                         */
                        .boost(2F)
                )
        );
        boolQuery.should(s -> s
                .match(m -> m
                        .field(SubjectFieldsElasticsearchConstant.SUBJECT_ANSWER)
                        .query(subjectInfoEs.getKeyWord())
                )
        );
        // must 条件 满足全部即可 相当于 and
        boolQuery.must(m -> m
                .match(ma -> ma
                        .field(SubjectFieldsElasticsearchConstant.SUBJECT_TYPE)
                        .query(SubjectTypeEnum.BRIEF.getCode())
                )
        );
        /*
         * 设置布尔查询中最小匹配条件。
         * 此方法用于指定布尔查询中应该匹配的最小应该匹配的子句数量。
         * 如果查询包含多个应该匹配（should）的子句，可以通过此方法设置至少需要匹配的子句数量。
         * 注意：此方法仅对布尔查询中的"should"子句生效，不影响"must"或"must_not"子句。</p>
         * 字符串表达式，用于设置最小匹配条件。该字符串可以是固定数字，
         * 或者是百分比形式（如"50%"，表示至少匹配一半的should子句）。
         * 如果设置为"1"，则表示至少要有一个should子句匹配。
         */
        boolQuery.minimumShouldMatch("1");
        // 配置要高亮显示的字段
        Map<String, HighlightField> highlightFields = new HashMap<>();
        highlightFields.put(SubjectFieldsElasticsearchConstant.SUBJECT_NAME, new HighlightField.Builder().build());
        highlightFields.put(SubjectFieldsElasticsearchConstant.SUBJECT_ANSWER, new HighlightField.Builder().build());
        // 构建高亮对象，设置前后标签及字段
        Highlight.Builder highlight = new Highlight.Builder();
        highlight.preTags("<span style = \"color:red\">");
        highlight.postTags("</span>");
        highlight.fields(highlightFields);
        // 强制字段匹配的条件为false。这意味着不严格要求字段之间的匹配。
        highlight.requireFieldMatch(false);
        // 设置搜索请求的查询条件、高亮、是否需要scroll、返回字段、分页信息
        esSearchRequest.setBoolQueryBuilder(boolQuery.build());
        esSearchRequest.setHighlightBuilder(highlight.build());
        esSearchRequest.setNeedScroll(false);
        esSearchRequest.setFields(SubjectFieldsElasticsearchConstant.FIELD_QUERY);
        esSearchRequest.setFrom((subjectInfoEs.getPageNo() - 1) * subjectInfoEs.getPageSize());
        esSearchRequest.setSize(subjectInfoEs.getPageSize());
        // 返回搜索请求对象
        return esSearchRequest;
    }

    /**
     * @author: 32115
     * @description: 插入数据到es
     * @date: 2024/5/28
     * @param: subjectInfoElasticsearch
     * @return: Boolean
     */
    @Override
    public Boolean insert(SubjectInfoElasticsearch subjectInfoElasticsearch) {
        // 封装es源数据
        ElasticsearchSourceData esSourceData = new ElasticsearchSourceData();
        Map<String, Object> data = convert2EsSourceData(subjectInfoElasticsearch);
        // 设置docId
        esSourceData.setDocId(subjectInfoElasticsearch.getDocId().toString());
        // 设置文档源数据
        esSourceData.setData(data);
        ElasticsearchIndexInfo esIndexInfo = getElasticsearchIndexInfo();
        // 执行插入
        return ElasticsearchUtil.insertDoc(esIndexInfo, esSourceData);
    }

    /**
     * @author: 32115
     * @description: 获取es索引信息
     * @date: 2024/5/30
     * @return: ElasticsearchIndexInfo
     */
    private static ElasticsearchIndexInfo getElasticsearchIndexInfo() {
        // 封装es索引和节点信息
        ElasticsearchIndexInfo esIndexInfo = new ElasticsearchIndexInfo();
        esIndexInfo.setClusterName("00ec21e3bba6");
        esIndexInfo.setIndexName("subject_index");
        return esIndexInfo;
    }

    /**
     * @author: 32115
     * @description: 将SubjectInfoElasticsearch对象转换为ES源数据的Map形式。
     * @date: 2024/5/30
     * @param: subjectInfoEs
     * @return: Map<String, Object>
     */
    private Map<String, Object> convert2EsSourceData(SubjectInfoElasticsearch subjectInfoEs) {
        Map<String, Object> data = new HashMap<>();
        // 将题目相关信息放入Map中，以便于构建ES文档
        data.put(SubjectFieldsElasticsearchConstant.SUBJECT_ID, subjectInfoEs.getSubjectId());
        data.put(SubjectFieldsElasticsearchConstant.DOC_ID, subjectInfoEs.getDocId());
        data.put(SubjectFieldsElasticsearchConstant.SUBJECT_NAME, subjectInfoEs.getSubjectName());
        data.put(SubjectFieldsElasticsearchConstant.SUBJECT_ANSWER, subjectInfoEs.getSubjectAnswer());
        data.put(SubjectFieldsElasticsearchConstant.SUBJECT_TYPE, subjectInfoEs.getSubjectType());
        data.put(SubjectFieldsElasticsearchConstant.CREATE_USER, subjectInfoEs.getCreateUser());
        data.put(SubjectFieldsElasticsearchConstant.CREATE_TIME, subjectInfoEs.getCreateTime());
        return data;
    }
}
