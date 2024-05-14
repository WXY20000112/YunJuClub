package com.wxy.subject.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectRadioBO实体类
 * @author: 32115
 * @create: 2024-05-13 11:44
 */
@Data
public class SubjectRadioBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -85190502716419235L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 选项类型
     */
    private Integer optionType;

    /**
     * 选项内容
     */
    private String optionContent;

    /**
     * 是否正确
     */
    private Integer isCorrect;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
