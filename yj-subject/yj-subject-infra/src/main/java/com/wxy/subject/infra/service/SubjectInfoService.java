package com.wxy.subject.infra.service;

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
}
