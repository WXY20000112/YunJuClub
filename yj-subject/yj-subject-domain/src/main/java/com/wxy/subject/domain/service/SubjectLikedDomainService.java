package com.wxy.subject.domain.service;

import com.wxy.subject.domain.entity.SubjectLikedBO;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLikedDomainService
 * @author: 32115
 * @create: 2024-05-31 15:24
 */
public interface SubjectLikedDomainService {

    // 添加点赞
    void addSubjectLiked(SubjectLikedBO subjectLikedBO);

    // 获取这个题目是否被当前用户点赞过
    Boolean isLiked(String subjectId, String userId);

    // 获取这个题目的点赞数量
    Integer getLikedCount(String subjectId);

    // 同步点赞数据到数据库
    void syncSubjectLiked();
}
