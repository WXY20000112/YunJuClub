package com.wxy.practice.server.service;

import com.mybatisflex.core.service.IService;
import com.wxy.practice.server.entity.PracticeSetDetail;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSetDetailService
 * @author: 32115
 * @create: 2024-06-05 10:44
 */
public interface PracticeSetDetailService extends IService<PracticeSetDetail> {

    // 根据setId查询
    List<PracticeSetDetail> getBySetId(Long setId);

    // 根据setId查询所有题目
    List<PracticeSetDetail> getPracticeSetDetailList(Long setId);
}
