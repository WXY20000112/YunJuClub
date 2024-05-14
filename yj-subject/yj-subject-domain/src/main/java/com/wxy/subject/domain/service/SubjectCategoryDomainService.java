package com.wxy.subject.domain.service;

import com.wxy.subject.domain.entity.SubjectCategoryBO;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategoryDomainService
 * @author: 32115
 * @create: 2024-05-13 11:54
 */
public interface SubjectCategoryDomainService{

    // 新增分类
    Boolean add(SubjectCategoryBO subjectCategoryBO);

    // 查询一级分类
    List<SubjectCategoryBO> queryPrimaryCategory(SubjectCategoryBO subjectCategoryBO);
}
