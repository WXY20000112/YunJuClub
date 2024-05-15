package com.wxy.subject.domain.handler;

import com.wxy.subject.common.enums.SubjectTypeEnum;
import com.wxy.subject.domain.entity.SubjectInfoBO;

/**
 * @program: YunJuClub-Flex
 * @description: 题目类型handler处理器
 * @author: 32115
 * @create: 2024-05-15 17:01
 */
public interface SubjectTypeHandler {

    // 用于获取当前策略类属于哪个枚举类型
    SubjectTypeEnum getHandlerType();

    // 添加题目功能
    Boolean addSubject(SubjectInfoBO subjectInfoBO);
}
