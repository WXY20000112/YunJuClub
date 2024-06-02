package com.wxy.subject.infra.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectInfo;
import com.wxy.subject.infra.mapper.SubjectInfoMapper;
import com.wxy.subject.infra.service.SubjectInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wxy.subject.infra.entity.table.SubjectInfoTableDef.SUBJECT_INFO;
import static com.wxy.subject.infra.entity.table.SubjectMappingTableDef.SUBJECT_MAPPING;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:01
 */
@Service
public class SubjectInfoServiceImpl
        extends ServiceImpl<SubjectInfoMapper, SubjectInfo>
        implements SubjectInfoService {

    @Resource
    private SubjectInfoMapper subjectInfoMapper;

    /**
     * @author: 32115
     * @description: 获取上一题和下一题的id
     * @date: 2024/6/1
     * @param: categoryId
     * @param: labelId
     * @param: id
     * @param: cursor
     * @return: Long
     */
    @Override
    public Long getLastSubjectId(Long categoryId, Long labelId, Long subjectId, int cursor) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SUBJECT_INFO.DEFAULT_COLUMNS)
                .from(SUBJECT_INFO.as("si"))
                .leftJoin(SUBJECT_MAPPING).as("sm").on(SUBJECT_INFO.ID.eq(SUBJECT_MAPPING.SUBJECT_ID))
                .where(SUBJECT_MAPPING.CATEGORY_ID.eq(categoryId))
                .and(SUBJECT_MAPPING.LABEL_ID.eq(labelId))
                .and(SUBJECT_INFO.ID.lt(subjectId, cursor == 0))
                .and(SUBJECT_INFO.ID.gt(subjectId, cursor == 1))
                .orderBy(SUBJECT_INFO.ID.asc());
        List<SubjectInfo> subjectInfoList = this.list(queryWrapper);
        return cursor == 0 ? subjectInfoList.getLast().getId() :
                subjectInfoList.getFirst().getId();
    }

    /**
     * @author: 32115
     * @description: 根据id查询题目
     * @date: 2024/5/16
     * @param: id
     * @return: SubjectInfo
     */
    @Override
    public SubjectInfo getSubjectInfoById(Long id) {
        return this.getById(id);
    }

    /**
     * @author: 32115
     * @description: 分页查询题目
     * @date: 2024/5/16
     * @param: pageNo
     * @param: pageSize
     * @return: Page<SubjectInfo>
     */
    @Override
    public Page<SubjectInfo> getSubjectPageList(
            Integer pageNo, Integer pageSize,
            Long categoryId, Long labelId, SubjectInfo subjectInfo) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SUBJECT_INFO.DEFAULT_COLUMNS)
                .from(SUBJECT_INFO.as("si"))
                .leftJoin(SUBJECT_MAPPING).as("sm").on(SUBJECT_INFO.ID.eq(SUBJECT_MAPPING.SUBJECT_ID))
                .where(SUBJECT_MAPPING.CATEGORY_ID.eq(categoryId))
                .and(SUBJECT_MAPPING.LABEL_ID.eq(labelId))
                .and(SUBJECT_INFO.SUBJECT_DIFFICULT.eq(subjectInfo.getSubjectDifficult()))
                .and(SUBJECT_INFO.SUBJECT_TYPE.eq(subjectInfo.getSubjectType()));
        return subjectInfoMapper.paginate(pageNo, pageSize, queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 添加题目
     * @date: 2024/5/15
     * @param: subjectInfo
     * @return: Boolean
     */
    @Override
    public Boolean addSubjectInfo(SubjectInfo subjectInfo) {
        return this.save(subjectInfo);
    }
}
