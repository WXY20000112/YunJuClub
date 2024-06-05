package com.wxy.practice.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSubjectDetailVO
 * @author: 32115
 * @create: 2024-06-04 14:44
 */
@Data
public class PracticeSubjectDetailVO implements Serializable {

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 题目类型
     */
    private Integer subjectType;

    /**
     * 是否回答
     */
    private Integer isAnswer;
}
