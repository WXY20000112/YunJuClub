package com.wxy.subject.infra.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.wxy.subject.infra.entity.SubjectInfo;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoService
 * @author: 32115
 * @create: 2024-05-13 11:54
 */
public interface SubjectInfoService extends IService<SubjectInfo> {
    // 添加题目
    Boolean addSubjectInfo(SubjectInfo subjectInfo);

    // 分页查询题目
    Page<SubjectInfo> getSubjectPageList(
            Integer pageNo, Integer pageSize,
            Long categoryId, Long labelId, SubjectInfo subjectInfo);

    // 根据id查询题目
    SubjectInfo getSubjectInfoById(Long id);

    // 查询当前题目的上一题与下一题的id
    Long getLastSubjectId(Long categoryId, Long labelId, Long subjectId, int cursor);
}
