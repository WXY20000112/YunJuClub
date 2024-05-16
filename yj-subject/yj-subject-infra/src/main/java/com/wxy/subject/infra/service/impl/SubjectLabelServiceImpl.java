package com.wxy.subject.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectLabel;
import com.wxy.subject.infra.mapper.SubjectLabelMapper;
import com.wxy.subject.infra.service.SubjectLabelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.distinct;
import static com.wxy.subject.infra.entity.table.SubjectInfoTableDef.SUBJECT_INFO;
import static com.wxy.subject.infra.entity.table.SubjectLabelTableDef.SUBJECT_LABEL;
import static com.wxy.subject.infra.entity.table.SubjectMappingTableDef.SUBJECT_MAPPING;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLabelServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:03
 */
@Service
public class SubjectLabelServiceImpl
        extends ServiceImpl<SubjectLabelMapper, SubjectLabel>
        implements SubjectLabelService {

    @Resource
    private SubjectLabelMapper subjectLabelMapper;

    /**
     * @author: 32115
     * @description: 根据题目id查询标签
     * @date: 2024/5/16
     * @param: id
     * @return: List<SubjectLabel>
     */
    @Override
    public List<SubjectLabel> getLabelBySubjectId(Long id) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(distinct(SUBJECT_LABEL.ID),
                        SUBJECT_LABEL.LABEL_NAME,
                        SUBJECT_LABEL.SORT_NUM,
                        SUBJECT_LABEL.IS_DELETED,
                        SUBJECT_LABEL.CREATED_BY,
                        SUBJECT_LABEL.CREATED_TIME,
                        SUBJECT_LABEL.UPDATE_BY,
                        SUBJECT_LABEL.UPDATE_TIME)
                .from(SUBJECT_INFO.as("si"))
                .leftJoin(SUBJECT_MAPPING).as("sm").on(SUBJECT_MAPPING.SUBJECT_ID.eq(SUBJECT_INFO.ID))
                .leftJoin(SUBJECT_LABEL).as("sl").on(SUBJECT_LABEL.ID.eq(SUBJECT_MAPPING.LABEL_ID))
                .where(SUBJECT_INFO.ID.eq(id));
        return this.list(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 根据id查询标签
     * @date: 2024/5/15
     * @param: labelIdList
     * @return: List<SubjectLabel>
     */
    @Override
    public List<SubjectLabel> getLabelById(List<Long> labelIdList) {
        // 返回查询结果
        return subjectLabelMapper.selectListByIds(labelIdList);
    }
}
