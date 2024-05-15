package com.wxy.subject.domain.service;

import com.wxy.subject.domain.entity.SubjectInfoBO;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoDomainService
 * @author: 32115
 * @create: 2024-05-13 11:54
 */
public interface SubjectInfoDomainService {

    // 添加题目
    Boolean addSubject(SubjectInfoBO subjectInfoBO);
}
