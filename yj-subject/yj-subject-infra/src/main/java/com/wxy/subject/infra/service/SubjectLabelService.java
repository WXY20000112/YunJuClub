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
}
