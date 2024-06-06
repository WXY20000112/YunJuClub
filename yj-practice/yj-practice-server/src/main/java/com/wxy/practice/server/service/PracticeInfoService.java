package com.wxy.practice.server.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.wxy.practice.api.common.PageInfo;
import com.wxy.practice.server.entity.PracticeInfo;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeInfoService
 * @author: 32115
 * @create: 2024-06-05 16:09
 */
public interface PracticeInfoService extends IService<PracticeInfo> {

    // 分页查询
    Page<PracticeInfo> getPracticePage(PageInfo pageInfo);
}
