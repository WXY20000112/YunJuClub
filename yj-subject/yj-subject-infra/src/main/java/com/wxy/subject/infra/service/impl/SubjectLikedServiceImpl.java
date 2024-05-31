package com.wxy.subject.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectLiked;
import com.wxy.subject.infra.mapper.SubjectLikedMapper;
import com.wxy.subject.infra.service.SubjectLikedService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLikedServiceImpl
 * @author: 32115
 * @create: 2024-05-31 15:24
 */
@Service
public class SubjectLikedServiceImpl extends
        ServiceImpl<SubjectLikedMapper, SubjectLiked>
        implements SubjectLikedService {

    /**
     * @author: 32115
     * @description: 批量插入点赞记录
     * @date: 2024/5/31
     * @param: subjectLikedList
     * @return: void
     */
    @Override
    public void insertSubjectLikedList(List<SubjectLiked> subjectLikedList) {
        this.saveBatch(subjectLikedList);
    }
}
