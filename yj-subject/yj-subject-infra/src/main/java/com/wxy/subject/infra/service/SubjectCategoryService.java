package com.wxy.subject.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.subject.infra.entity.SubjectCategory;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategoryService
 * @author: 32115
 * @create: 2024-05-13 11:54
 */
public interface SubjectCategoryService extends IService<SubjectCategory> {

    // 获取一级分类
    List<SubjectCategory> getPrimaryCategoryList(SubjectCategory subjectCategory);

    // 获取二级分类数量
    Integer getSecondCategoryCount(Long id);
}
