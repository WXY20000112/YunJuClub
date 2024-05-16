package com.wxy.subject.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectMultiple;
import com.wxy.subject.infra.mapper.SubjectMultipleMapper;
import com.wxy.subject.infra.service.SubjectMultipleService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wxy.subject.infra.entity.table.SubjectMultipleTableDef.SUBJECT_MULTIPLE;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectMultipleServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:04
 */
@Service
public class SubjectMultipleServiceImpl
        extends ServiceImpl<SubjectMultipleMapper, SubjectMultiple>
        implements SubjectMultipleService {

    /**
     * @author: 32115
     * @description: 根据题目id查询多选题
     * @date: 2024/5/16
     * @param: id
     * @return: List<SubjectMultiple>
     */
    @Override
    public List<SubjectMultiple> getBySubjectId(Long id) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .from(SUBJECT_MULTIPLE)
                .where(SUBJECT_MULTIPLE.SUBJECT_ID.eq(id));
        return this.list(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 添加多选题
     * @date: 2024/5/15
     * @param: subjectMultipleList
     * @return: Boolean
     */
    @Override
    public Boolean addMultipleSubject(List<SubjectMultiple> subjectMultipleList) {
        return this.saveBatch(subjectMultipleList);
    }
}
