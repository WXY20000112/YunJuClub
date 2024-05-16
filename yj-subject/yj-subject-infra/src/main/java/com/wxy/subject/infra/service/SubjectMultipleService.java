package com.wxy.subject.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.subject.infra.entity.SubjectMultiple;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectMultipleService
 * @author: 32115
 * @create: 2024-05-13 11:57
 */
public interface SubjectMultipleService extends IService<SubjectMultiple> {

    // 添加多选题
    Boolean addMultipleSubject(List<SubjectMultiple> subjectMultipleList);

    // 根据题目id查询多选题
    List<SubjectMultiple> getBySubjectId(Long id);
}
