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

    // 更新分类
    Boolean updateCategory(SubjectCategory subjectCategory);

    // 添加分类
    Boolean insertCategory(SubjectCategory subjectCategory);

    // 删除分类
    Boolean deleteCategory(SubjectCategory subjectCategory);

    // 根据分类id获取分类
    SubjectCategory getCategoryById(Long categoryId);

    // 根据题目类型查询二级分类
    List<SubjectCategory> getCategoryBySubjectType(List<Integer> subjectTypeList);
}
