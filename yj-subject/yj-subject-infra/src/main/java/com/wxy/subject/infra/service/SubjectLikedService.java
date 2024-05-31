package com.wxy.subject.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.subject.infra.entity.SubjectLiked;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLikedService
 * @author: 32115
 * @create: 2024-05-31 15:24
 */
public interface SubjectLikedService extends IService<SubjectLiked> {

    // 批量插入
    void insertSubjectLikedList(List<SubjectLiked> subjectLikedList);
}
