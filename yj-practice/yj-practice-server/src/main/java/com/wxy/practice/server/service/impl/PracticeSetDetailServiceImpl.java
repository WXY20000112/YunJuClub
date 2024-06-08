package com.wxy.practice.server.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.practice.server.entity.PracticeSetDetail;
import com.wxy.practice.server.mapper.PracticeSetDetailMapper;
import com.wxy.practice.server.service.PracticeSetDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wxy.practice.server.entity.table.PracticeSetDetailTableDef.PRACTICE_SET_DETAIL;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSetDetailServiceImpl
 * @author: 32115
 * @create: 2024-06-05 10:45
 */
@Service
public class PracticeSetDetailServiceImpl extends
        ServiceImpl<PracticeSetDetailMapper, PracticeSetDetail>
        implements PracticeSetDetailService {

    /**
     * @author: 32115
     * @description: 根据setId查询所有题目
     * @date: 2024/6/8
     * @param: setId
     * @return: List<PracticeSetDetail>
     */
    @Override
    public List<PracticeSetDetail> getPracticeSetDetailList(Long setId) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRACTICE_SET_DETAIL.DEFAULT_COLUMNS)
                .from(PRACTICE_SET_DETAIL)
                .where(PRACTICE_SET_DETAIL.SET_ID.eq(setId))
                .orderBy(PRACTICE_SET_DETAIL.SUBJECT_TYPE.desc());
        return this.list(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 根据setId查询详情
     * @date: 2024/6/5
     * @param: setId
     * @return: List<PracticeSetDetail>
     */
    @Override
    public List<PracticeSetDetail> getBySetId(Long setId) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRACTICE_SET_DETAIL.DEFAULT_COLUMNS)
                .from(PRACTICE_SET_DETAIL)
                .where(PRACTICE_SET_DETAIL.SET_ID.eq(setId));
        return this.list(queryWrapper);
    }
}
