package com.wxy.practice.server.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.practice.server.entity.PracticeInfo;
import com.wxy.practice.server.mapper.PracticeInfoMapper;
import com.wxy.practice.server.service.PracticeInfoService;
import org.springframework.stereotype.Service;

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
}
