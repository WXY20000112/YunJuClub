package com.wxy.subject.domain.service.impl;

import com.wxy.subject.domain.entity.SubjectCategoryBO;
import com.wxy.subject.domain.service.SubjectCategoryDomainService;
import com.wxy.subject.infra.entity.SubjectCategory;
import com.wxy.subject.infra.service.SubjectCategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategoryDomainServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:00
 */
@Service
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    @Resource
    private SubjectCategoryService subjectCategoryService;


    @Override
    public Boolean add(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = new SubjectCategory();
        subjectCategory.setCategoryName(subjectCategoryBO.getCategoryName());
        subjectCategory.setParentId(subjectCategoryBO.getParentId());
        subjectCategory.setCategoryType(subjectCategoryBO.getCategoryType());
        subjectCategory.setCategoryIcon(subjectCategoryBO.getCategoryIcon());
        return subjectCategoryService.save(subjectCategory);
    }
}
