package com.wxy.subject.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectLabel;
import com.wxy.subject.infra.mapper.SubjectLabelMapper;
import com.wxy.subject.infra.service.SubjectLabelService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLabelServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:03
 */
@Service
public class SubjectLabelServiceImpl
        extends ServiceImpl<SubjectLabelMapper, SubjectLabel>
        implements SubjectLabelService {
}
