package com.wxy.practice.server.service;

import com.mybatisflex.core.service.IService;
import com.wxy.practice.server.entity.PracticeDetail;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeDetailService
 * @author: 32115
 * @create: 2024-06-05 15:17
 */
public interface PracticeDetailService extends IService<PracticeDetail> {

    // 根据练习id和题目id查询练习详情
    PracticeDetail getPracticeDetailByPracticeIdAndSubjectId(
            Long practiceId, Long subjectId, String loginId);
}
