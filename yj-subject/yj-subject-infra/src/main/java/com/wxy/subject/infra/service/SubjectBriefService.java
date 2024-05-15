package com.wxy.subject.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.subject.infra.entity.SubjectBrief;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectBriefService
 * @author: 32115
 * @create: 2024-05-13 11:52
 */
public interface SubjectBriefService extends IService<SubjectBrief> {

    // 添加简答题
    Boolean addBriefSubject(SubjectBrief subjectBrief);
}
