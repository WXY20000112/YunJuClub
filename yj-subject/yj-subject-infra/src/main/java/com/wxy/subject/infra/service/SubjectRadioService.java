package com.wxy.subject.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.subject.infra.entity.SubjectRadio;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectRadioService
 * @author: 32115
 * @create: 2024-05-13 11:57
 */
public interface SubjectRadioService extends IService<SubjectRadio> {

    // 添加单选题
    Boolean addRadioSubject(List<SubjectRadio> subjectRadioList);
}
