package com.wxy.subject.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectMultipleBO实体类
 * @author: 32115
 * @create: 2024-05-13 11:43
 */
@Data
public class SubjectMultipleBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -31306065624527765L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 题目类型
     */
    private Integer multipleType;

    /**
     * 选项内容
     */
    private String multipleContent;

    /**
     * 是否正确
     */
    private Integer isCorrect;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
