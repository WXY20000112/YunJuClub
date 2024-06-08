package com.wxy.subject.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.common.enums.IsDeletedEnum;
import com.wxy.subject.infra.entity.SubjectMapping;
import com.wxy.subject.infra.mapper.SubjectMappingMapper;
import com.wxy.subject.infra.service.SubjectMappingService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.distinct;
import static com.wxy.subject.infra.entity.table.SubjectMappingTableDef.SUBJECT_MAPPING;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectMappingServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:04
 */
@Service
public class SubjectMappingServiceImpl
        extends ServiceImpl<SubjectMappingMapper, SubjectMapping>
        implements SubjectMappingService {

    /**
     * @author: 32115
     * @description: 根据题目id查询映射关系
     * @date: 2024/6/8
     * @param: subjectId
     * @return: List<SubjectMapping>
     */
    @Override
    public List<SubjectMapping> getBySubjectId(Long subjectId) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(distinct(SUBJECT_MAPPING.LABEL_ID),
                        SUBJECT_MAPPING.SUBJECT_ID)
                .from(SUBJECT_MAPPING)
                .where(SUBJECT_MAPPING.SUBJECT_ID.eq(subjectId));
        return this.list(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 批量添加映射关系
     * @date: 2024/5/15
     * @param: subjectMappingList
     * @return: Boolean
     */
    @Override
    public Boolean addSubjectMapping(List<SubjectMapping> subjectMappingList) {
        return this.saveBatch(subjectMappingList);
    }

    /**
     * @author: 32115
     * @description: 根据分类id查询映射关系
     * @date: 2024/5/15
     * @param: id
     * @return: List<SubjectMapping>
     */
    @Override
    public List<SubjectMapping> getMappingByCategoryId(Long id) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .from(SUBJECT_MAPPING)
                .where(SUBJECT_MAPPING.CATEGORY_ID.eq(id))
                .and(SUBJECT_MAPPING.IS_DELETED.eq(IsDeletedEnum.UN_DELETED.getCode()));
        // 执行查询
        return this.list(queryWrapper);
    }
}
