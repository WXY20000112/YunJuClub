package com.wxy.subject.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.subject.infra.entity.SubjectJudge;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectJudgeService
 * @author: 32115
 * @create: 2024-05-13 11:55
 */
public interface SubjectJudgeService extends IService<SubjectJudge> {

    // 添加判断题
    Boolean addJudgeSubject(SubjectJudge subjectJudge);
}
