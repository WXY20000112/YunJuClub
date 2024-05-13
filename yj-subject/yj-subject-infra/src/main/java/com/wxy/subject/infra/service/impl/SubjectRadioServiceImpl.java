package com.wxy.subject.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectRadio;
import com.wxy.subject.infra.mapper.SubjectRadioMapper;
import com.wxy.subject.infra.service.SubjectRadioService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectRadioServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:05
 */
@Service
public class SubjectRadioServiceImpl
        extends ServiceImpl<SubjectRadioMapper, SubjectRadio>
        implements SubjectRadioService {
}
