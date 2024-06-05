package com.wxy.subject.domain.service;

import com.wxy.subject.domain.entity.SubjectLabelBO;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLabelDomainService
 * @author: 32115
 * @create: 2024-05-13 11:56
 */
public interface SubjectLabelDomainService {

    // 添加标签
    Boolean addLabel(SubjectLabelBO subjectLabelBO);

    // 删除标签
    Boolean deleteLabel(SubjectLabelBO subjectLabelBO);

    // 更新标签
    Boolean updateLabel(SubjectLabelBO subjectLabelBO);

    // 根据标签id查询标签信息
    List<SubjectLabelBO> getLabelByCategoryId(SubjectLabelBO subjectLabelBO);

    // 根据分类id和题目类型查询标签信息
    List<SubjectLabelBO> getLabelByCategoryIdAndSubjectType(Long categoryId);

    // 根据标签id查询标签信息
    SubjectLabelBO getLabelById(Long id);
}
