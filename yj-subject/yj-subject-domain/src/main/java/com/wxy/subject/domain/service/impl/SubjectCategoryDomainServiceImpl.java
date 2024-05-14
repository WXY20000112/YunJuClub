package com.wxy.subject.domain.service.impl;

import com.wxy.subject.domain.converter.SubjectCategoryBOConverter;
import com.wxy.subject.domain.entity.SubjectCategoryBO;
import com.wxy.subject.domain.service.SubjectCategoryDomainService;
import com.wxy.subject.infra.entity.SubjectCategory;
import com.wxy.subject.infra.service.SubjectCategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * @author: 32115
     * @description: 新增分类
     * @date: 2024/5/14
     * @param: subjectCategoryBO
     * @return: Boolean
     */
    @Override
    public Boolean add(SubjectCategoryBO subjectCategoryBO) {
        // BO转category实体类
        SubjectCategory subjectCategory = SubjectCategoryBOConverter
                .CONVERTER.convertCategoryBoToCategory(subjectCategoryBO);
        // 保存分类信息
        return subjectCategoryService.save(subjectCategory);
    }

    /**
     * @author: 32115
     * @description: 查询一级分类
     * @date: 2024/5/14
     * @param: subjectCategoryBO
     * @return: List<SubjectCategoryBO>
     */
    @Override
    public List<SubjectCategoryBO> queryPrimaryCategory(SubjectCategoryBO subjectCategoryBO) {
        // bo转category
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.CONVERTER
                .convertCategoryBoToCategory(subjectCategoryBO);
        // 查询一级分类
        List<SubjectCategory> subjectCategoryList =
                subjectCategoryService.getPrimaryCategoryList(subjectCategory);
        // category转bo
        // 将SubjectCategory转换为SubjectCategoryBO
        List<SubjectCategoryBO> boList = SubjectCategoryBOConverter.CONVERTER
                .convertCategoryToBO(subjectCategoryList);
        // 查询分类下的二级分类的数量
        boList.forEach(bo -> {
            // 获取二级分类数量
            bo.setCount(subjectCategoryService.getSecondCategoryCount(bo.getId()));
        });
        // 返回结果
        return boList;
    }
}
