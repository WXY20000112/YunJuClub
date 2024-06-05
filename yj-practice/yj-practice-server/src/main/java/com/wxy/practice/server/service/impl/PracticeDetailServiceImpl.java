package com.wxy.practice.server.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.practice.server.entity.PracticeDetail;
import com.wxy.practice.server.mapper.PracticeDetailMapper;
import com.wxy.practice.server.service.PracticeDetailService;
import org.springframework.stereotype.Service;

import static com.wxy.practice.server.entity.table.PracticeDetailTableDef.PRACTICE_DETAIL;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeDetailServiceImpl
 * @author: 32115
 * @create: 2024-06-05 15:18
 */
@Service
public class PracticeDetailServiceImpl
            extends ServiceImpl<PracticeDetailMapper, PracticeDetail>
            implements PracticeDetailService {

    /**
     * @author: 32115
     * @description: 根据练习id和题目id查询练习详情
     * @date: 2024/6/5
     * @param: practiceId
     * @param: subjectId
     * @param: loginId
     * @return: PracticeDetail
     */
    @Override
    public PracticeDetail getPracticeDetailByPracticeIdAndSubjectId(
            Long practiceId, Long subjectId, String loginId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRACTICE_DETAIL.DEFAULT_COLUMNS)
                .from(PRACTICE_DETAIL)
                .where(PRACTICE_DETAIL.PRACTICE_ID.eq(practiceId))
                .and(PRACTICE_DETAIL.SUBJECT_ID.eq(subjectId))
                .and(PRACTICE_DETAIL.CREATED_BY.eq(loginId));
        return this.getOne(queryWrapper);
    }
}
