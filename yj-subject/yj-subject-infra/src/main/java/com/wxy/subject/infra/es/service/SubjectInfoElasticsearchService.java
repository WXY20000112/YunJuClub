package com.wxy.subject.infra.es.service;

import com.mybatisflex.core.paginate.Page;
import com.wxy.subject.infra.es.entity.SubjectInfoElasticsearch;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoElasticsearchService
 * @author: 32115
 * @create: 2024-05-28 12:39
 */
public interface SubjectInfoElasticsearchService {

    // 插入
    Boolean insert(SubjectInfoElasticsearch subjectInfoElasticsearch);

    // 分页高亮查询
    Page<SubjectInfoElasticsearch> getSubjectPageList(SubjectInfoElasticsearch subjectInfoElasticsearch);
}
