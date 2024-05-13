package com.wxy.subject.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectMapping;
import com.wxy.subject.infra.mapper.SubjectMappingMapper;
import com.wxy.subject.infra.service.SubjectMappingService;
import org.springframework.stereotype.Service;

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
}
