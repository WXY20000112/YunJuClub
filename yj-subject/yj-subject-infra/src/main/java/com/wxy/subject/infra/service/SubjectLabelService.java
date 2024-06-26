package com.wxy.subject.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.subject.infra.entity.SubjectLabel;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLabelService
 * @author: 32115
 * @create: 2024-05-13 11:56
 */
public interface SubjectLabelService extends IService<SubjectLabel> {

    // 根据id查询标签
    List<SubjectLabel> getLabelById(List<Long> labelIdList);

    // 根据题目id查询标签
    List<SubjectLabel> getLabelBySubjectId(Long id);

    // 添加标签
    Boolean addLabelInfo(SubjectLabel subjectLabel);

    // 删除标签
    Boolean deleteLabel(SubjectLabel subjectLabel);

    // 修改标签
    Boolean updateLabel(SubjectLabel subjectLabel);

    // 根据一级分类id查询标签
    List<SubjectLabel> getByCategoryId(Long categoryId);

    // 根据二级分类id查询标签
    List<SubjectLabel> getLabelListByCategoryId(Long categoryId);

    // 根据二级分类id和题目类型查询标签
    List<SubjectLabel> getLabelByCategoryId(Long categoryId, List<Integer> subjectTypeList);

    // 根据标签id查询标签
    SubjectLabel getLabelByLabelId(Long id);
}
