package com.wxy.subject.domain.service;

import com.mybatisflex.core.paginate.Page;
import com.wxy.subject.domain.entity.SubjectInfoBO;
import com.wxy.subject.infra.es.entity.SubjectInfoElasticsearch;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoDomainService
 * @author: 32115
 * @create: 2024-05-13 11:54
 */
public interface SubjectInfoDomainService {

    // 添加题目
    Boolean addSubject(SubjectInfoBO subjectInfoBO);

    // 分页查询题目列表
    Page<SubjectInfoBO> getSubjectPageList(SubjectInfoBO subjectInfoBO);

    // 查询题目详情
    SubjectInfoBO getSubjectInfo(SubjectInfoBO subjectInfoBO);

    // 全文检索分页查询
    Page<SubjectInfoElasticsearch> getSubjectPageByElasticsearch(SubjectInfoBO subjectInfoBO);

    // 查询贡献榜
    List<SubjectInfoBO> getContributeList();
}
