package com.wxy.subject.domain.service.impl;

import com.wxy.subject.common.aop.AopLogAnnotations;
import com.wxy.subject.common.enums.CategoryTypeEnum;
import com.wxy.subject.domain.converter.SubjectLabelBOConverter;
import com.wxy.subject.domain.entity.SubjectLabelBO;
import com.wxy.subject.domain.service.SubjectLabelDomainService;
import com.wxy.subject.infra.entity.SubjectCategory;
import com.wxy.subject.infra.entity.SubjectLabel;
import com.wxy.subject.infra.service.SubjectCategoryService;
import com.wxy.subject.infra.service.SubjectLabelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLabelDomainServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:03
 */
@Service
public class SubjectLabelDomainServiceImpl implements SubjectLabelDomainService {

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectCategoryService subjectCategoryService;

    /**
     * @author: 32115
     * @description: 根据分类id查询标签信息
     * @date: 2024/5/16
     * @param: subjectLabelBO
     * @return: List<SubjectLabelBO>
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public List<SubjectLabelBO> getLabelByCategoryId(SubjectLabelBO subjectLabelBO) {
        // 首先根据categoryId查询分类信息
        // 如果要查询的分类是一级分类 直接查询label表返回一级分类下的所有label即可
        SubjectCategory subjectCategory = subjectCategoryService
                .getCategoryById(subjectLabelBO.getCategoryId());
        if (subjectCategory.getCategoryType().equals(CategoryTypeEnum.FIRST_CATEGORY.getCode())){
            // 若是一级分类直接查询一级分类下的所有label
            List<SubjectLabel> subjectLabelList = subjectLabelService
                    .getByCategoryId(subjectLabelBO.getCategoryId());
            // 转换为BO并返回
            return SubjectLabelBOConverter
                    .CONVERTER.converterLabelToBo(subjectLabelList);
        }
        // 否则根据mapping关联关系查询二级分类下的所有label
        List<SubjectLabel> subjectLabelList = subjectLabelService
                .getLabelListByCategoryId(subjectLabelBO.getCategoryId());
        // 如果有二级分类下的label为空，返回一个空集合
        if (CollectionUtils.isEmpty(subjectLabelList)) return Collections.emptyList();
        // 否则转换为BO并返回
        return SubjectLabelBOConverter
                .CONVERTER.converterLabelToBo(subjectLabelList);
    }

    /**
     * @author: 32115
     * @description: 修改标签信息
     * @date: 2024/5/16
     * @param: subjectLabelBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean updateLabel(SubjectLabelBO subjectLabelBO) {
        // Bo 转换为实体
        SubjectLabel subjectLabel = SubjectLabelBOConverter
                .CONVERTER.converterBoToLabel(subjectLabelBO);
        // 修改标签
        return subjectLabelService.updateLabel(subjectLabel);
    }

    /**
     * @author: 32115
     * @description: 删除标签
     * @date: 2024/5/16
     * @param: subjectLabelBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean deleteLabel(SubjectLabelBO subjectLabelBO) {
        // Bo 转换为实体
        SubjectLabel subjectLabel = SubjectLabelBOConverter
                .CONVERTER.converterBoToLabel(subjectLabelBO);
        // 删除标签
        return subjectLabelService.deleteLabel(subjectLabel);
    }

    /**
     * @author: 32115
     * @description: 添加标签
     * @date: 2024/5/16
     * @param: subjectLabelBO
     * @return: boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean addLabel(SubjectLabelBO subjectLabelBO) {
        // BO转换为实体
        SubjectLabel subjectLabel = SubjectLabelBOConverter
                .CONVERTER.converterBoToLabel(subjectLabelBO);
        // 添加标签
        return subjectLabelService.addLabelInfo(subjectLabel);
    }
}
