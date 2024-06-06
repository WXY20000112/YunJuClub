package com.wxy.practice.server.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.practice.api.common.PageInfo;
import com.wxy.practice.server.entity.PracticeInfo;
import com.wxy.practice.server.enums.PracticeSetEnum;
import com.wxy.practice.server.mapper.PracticeInfoMapper;
import com.wxy.practice.server.service.PracticeInfoService;
import com.wxy.practice.server.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static com.wxy.practice.server.entity.table.PracticeInfoTableDef.PRACTICE_INFO;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeInfoServiceImpl
 * @author: 32115
 * @create: 2024-06-05 16:10
 */
@Service
public class PracticeInfoServiceImpl
        extends ServiceImpl<PracticeInfoMapper, PracticeInfo>
        implements PracticeInfoService {

    @Resource
    private PracticeInfoMapper practiceInfoMapper;

    /**
     * @author: 32115
     * @description: 分页查询获取练习信息
     * @date: 2024/6/6
     * @param: pageInfo
     * @return: Page<PracticeInfo>
     */
    @Override
    public Page<PracticeInfo> getPracticePage(PageInfo pageInfo) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRACTICE_INFO.DEFAULT_COLUMNS)
                .from(PRACTICE_INFO)
                .where(PRACTICE_INFO.COMPLETE_STATUS.eq(PracticeSetEnum.UN_COMPLETE.getCode()))
                .and(PRACTICE_INFO.CREATED_BY.eq(ThreadLocalUtil.getLoginId()))
                .orderBy(PRACTICE_INFO.SUBMIT_TIME.desc());
        return practiceInfoMapper.paginate(
                pageInfo.getPageNo(), pageInfo.getPageSize(), queryWrapper);
    }
}
