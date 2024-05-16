package com.wxy.subject.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectBrief;
import com.wxy.subject.infra.mapper.SubjectBriefMapper;
import com.wxy.subject.infra.service.SubjectBriefService;
import org.springframework.stereotype.Service;

import static com.wxy.subject.infra.entity.table.SubjectBriefTableDef.SUBJECT_BRIEF;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectBriefServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 11:59
 */
@Service
public class SubjectBriefServiceImpl
        extends ServiceImpl<SubjectBriefMapper, SubjectBrief>
        implements SubjectBriefService {

    /**
     * @author: 32115
     * @description: 根据题目id查询简答题目
     * @date: 2024/5/16
     * @param: id
     * @return: SubjectBrief
     */
    @Override
    public SubjectBrief getBySubjectId(Long id) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .from(SUBJECT_BRIEF)
                .where(SUBJECT_BRIEF.SUBJECT_ID.eq(id));
        return this.getOne(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 添加简答题目
     * @date: 2024/5/15
     * @param: subjectBrief
     * @return: Boolean
     */
    @Override
    public Boolean addBriefSubject(SubjectBrief subjectBrief) {
        return this.save(subjectBrief);
    }
}
