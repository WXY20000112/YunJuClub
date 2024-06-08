package com.wxy.subject.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.subject.infra.entity.SubjectMapping;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectMappingService
 * @author: 32115
 * @create: 2024-05-13 11:56
 */
public interface SubjectMappingService extends IService<SubjectMapping> {

    // 根据分类id查询映射关系
    List<SubjectMapping> getMappingByCategoryId(Long id);


    // 批量添加映射关系
    Boolean addSubjectMapping(List<SubjectMapping> subjectMappingList);

    // 根据题目id查询映射关系
    List<SubjectMapping> getBySubjectId(Long subjectId);
}
