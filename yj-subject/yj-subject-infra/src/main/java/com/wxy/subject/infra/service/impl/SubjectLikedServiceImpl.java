package com.wxy.subject.infra.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.common.enums.SubjectLikedStatusEnum;
import com.wxy.subject.infra.entity.SubjectLiked;
import com.wxy.subject.infra.mapper.SubjectLikedMapper;
import com.wxy.subject.infra.service.SubjectLikedService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wxy.subject.infra.entity.table.SubjectLikedTableDef.SUBJECT_LIKED;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLikedServiceImpl
 * @author: 32115
 * @create: 2024-05-31 15:24
 */
@Service
public class SubjectLikedServiceImpl extends
        ServiceImpl<SubjectLikedMapper, SubjectLiked>
        implements SubjectLikedService {

    @Resource
    private SubjectLikedMapper subjectLikedMapper;

    /**
     * @author: 32115
     * @description: 更新点赞记录
     * @date: 2024/6/2
     * @param: subjectUpdateLikedList
     * @return: void
     */
    @Override
    public void updateSubjectLikedList(List<SubjectLiked> subjectUpdateLikedList) {
        // 因为不是根据主键id进行更新 所以不能用updateBatch方法 该方法要求主键不能为空
        // 所以遍历列表根据查询条件一个一个更新
        subjectUpdateLikedList.forEach(subjectLiked -> {
            // 构造查询条件 根据查询条件进行更新
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .select(SUBJECT_LIKED.DEFAULT_COLUMNS)
                    .from(SUBJECT_LIKED)
                    .where(SUBJECT_LIKED.LIKE_USER_ID.eq(subjectLiked.getLikeUserId()))
                    .and(SUBJECT_LIKED.SUBJECT_ID.eq(subjectLiked.getSubjectId()));
            this.update(subjectLiked, queryWrapper);
        });
    }

    /**
     * @author: 32115
     * @description: 判断是否存在点赞记录
     * @date: 2024/6/2
     * @param: subjectLiked
     * @return: Boolean
     */
    @Override
    public Boolean existSubjectLiked(SubjectLiked subjectLiked) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SUBJECT_LIKED.DEFAULT_COLUMNS)
                .from(SUBJECT_LIKED)
                .where(SUBJECT_LIKED.LIKE_USER_ID.eq(subjectLiked.getLikeUserId()))
                .and(SUBJECT_LIKED.SUBJECT_ID.eq(subjectLiked.getSubjectId()));
        return this.exists(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 分页查询点赞记录
     * @date: 2024/6/1
     * @param: pageNo
     * @param: pageSize
     * @param: loginId
     * @return: Page<SubjectLiked>
     */
    @Override
    public Page<SubjectLiked> getSubjectLikedPage(Integer pageNo, Integer pageSize, String loginId) {
        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SUBJECT_LIKED.DEFAULT_COLUMNS)
                .from(SUBJECT_LIKED)
                .where(SUBJECT_LIKED.LIKE_USER_ID.eq(loginId))
                .and(SUBJECT_LIKED.STATUS.eq(SubjectLikedStatusEnum.LIKED.getCode()));
        return subjectLikedMapper.paginate(pageNo, pageSize, queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 批量插入点赞记录
     * @date: 2024/5/31
     * @param: subjectLikedList
     * @return: void
     */
    @Override
    public void insertSubjectLikedList(List<SubjectLiked> subjectLikedList) {
        this.saveBatch(subjectLikedList);
    }
}
