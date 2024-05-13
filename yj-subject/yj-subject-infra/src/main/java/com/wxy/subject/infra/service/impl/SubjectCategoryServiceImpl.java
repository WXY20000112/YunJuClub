package com.wxy.subject.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectCategory;
import com.wxy.subject.infra.mapper.SubjectCategoryMapper;
import com.wxy.subject.infra.service.SubjectCategoryService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategoryServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:00
 */
@Service
public class SubjectCategoryServiceImpl
        extends ServiceImpl<SubjectCategoryMapper, SubjectCategory>
        implements SubjectCategoryService {
}
