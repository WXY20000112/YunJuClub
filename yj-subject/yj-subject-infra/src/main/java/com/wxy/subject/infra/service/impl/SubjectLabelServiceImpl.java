package com.wxy.subject.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.infra.entity.SubjectLabel;
import com.wxy.subject.infra.mapper.SubjectLabelMapper;
import com.wxy.subject.infra.service.SubjectLabelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLabelServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:03
 */
@Service
public class SubjectLabelServiceImpl
        extends ServiceImpl<SubjectLabelMapper, SubjectLabel>
        implements SubjectLabelService {

    @Resource
    private SubjectLabelMapper subjectLabelMapper;

    /**
     * @author: 32115
     * @description: 根据id查询标签
     * @date: 2024/5/15
     * @param: labelIdList
     * @return: List<SubjectLabel>
     */
    @Override
    public List<SubjectLabel> getLabelById(List<Long> labelIdList) {

        // 返回查询结果
        return subjectLabelMapper.selectListByIds(labelIdList);
    }
}
