package com.wxy.practice.server.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.practice.server.entity.PracticeSetDetail;
import com.wxy.practice.server.mapper.PracticeSetDetailMapper;
import com.wxy.practice.server.service.PracticeSetDetailService;
import org.springframework.stereotype.Service;

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
}
