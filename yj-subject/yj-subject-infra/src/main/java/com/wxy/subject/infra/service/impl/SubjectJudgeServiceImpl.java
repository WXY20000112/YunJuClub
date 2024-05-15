package com.wxy.subject.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectJudge;
import com.wxy.subject.infra.mapper.SubjectJudgeMapper;
import com.wxy.subject.infra.service.SubjectJudgeService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectJudgeServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:02
 */
@Service
public class SubjectJudgeServiceImpl
        extends ServiceImpl<SubjectJudgeMapper, SubjectJudge>
        implements SubjectJudgeService {

    /**
     * @author: 32115
     * @description: 添加判断题
     * @date: 2024/5/15
     * @param: subjectJudge
     * @return: Boolean
     */
    @Override
    public Boolean addJudgeSubject(SubjectJudge subjectJudge) {
        return this.save(subjectJudge);
    }
}
