package com.wxy.subject.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectInfo;
import com.wxy.subject.infra.mapper.SubjectInfoMapper;
import com.wxy.subject.infra.service.SubjectInfoService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:01
 */
@Service
public class SubjectInfoServiceImpl
        extends ServiceImpl<SubjectInfoMapper, SubjectInfo>
        implements SubjectInfoService {

    /**
     * @author: 32115
     * @description: 添加题目
     * @date: 2024/5/15
     * @param: subjectInfo
     * @return: Boolean
     */
    @Override
    public Boolean addSubjectInfo(SubjectInfo subjectInfo) {
        return this.save(subjectInfo);
    }
}
