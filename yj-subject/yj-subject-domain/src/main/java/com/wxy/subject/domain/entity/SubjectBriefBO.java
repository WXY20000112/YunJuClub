package com.wxy.subject.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectBriefBO实体类
 * @author: 32115
 * @create: 2024-05-13 11:28
 */
@Data
public class SubjectBriefBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -76141031605368189L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 题目答案
     */
    private String subjectAnswer;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
