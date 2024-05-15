package com.wxy.subject.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectMultiple;
import com.wxy.subject.infra.mapper.SubjectMultipleMapper;
import com.wxy.subject.infra.service.SubjectMultipleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectMultipleServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:04
 */
@Service
public class SubjectMultipleServiceImpl
        extends ServiceImpl<SubjectMultipleMapper, SubjectMultiple>
        implements SubjectMultipleService {

    /**
     * @author: 32115
     * @description: 添加多选题
     * @date: 2024/5/15
     * @param: subjectMultipleList
     * @return: Boolean
     */
    @Override
    public Boolean addMultipleSubject(List<SubjectMultiple> subjectMultipleList) {
        return this.saveBatch(subjectMultipleList);
    }
}
