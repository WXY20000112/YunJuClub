package com.wxy.subject.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectJudgeBO实体类
 * @author: 32115
 * @create: 2024-05-13 11:38
 */
@Data
public class SubjectJudgeBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -89009200151001273L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 是否正确
     */
    private Integer isCorrect;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
