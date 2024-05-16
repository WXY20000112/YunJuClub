package com.wxy.subject.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectRadio;
import com.wxy.subject.infra.mapper.SubjectRadioMapper;
import com.wxy.subject.infra.service.SubjectRadioService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wxy.subject.infra.entity.table.SubjectRadioTableDef.SUBJECT_RADIO;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectRadioServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:05
 */
@Service
public class SubjectRadioServiceImpl
        extends ServiceImpl<SubjectRadioMapper, SubjectRadio>
        implements SubjectRadioService {

    /**
     * @author: 32115
     * @description: 根据题目id查询单选题
     * @date: 2024/5/16
     * @param: id
     * @return: List<SubjectRadio>
     */
    @Override
    public List<SubjectRadio> getBySubjectId(Long id) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .from(SUBJECT_RADIO)
                .where(SUBJECT_RADIO.SUBJECT_ID.eq(id));
        return this.list(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 添加单选题
     * @date: 2024/5/15
     * @param: subjectRadioList
     * @return: Boolean
     */
    @Override
    public Boolean addRadioSubject(List<SubjectRadio> subjectRadioList) {
        return this.saveBatch(subjectRadioList);
    }
}
