package com.wxy.subject.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectMappingBO实体类
 * @author: 32115
 * @create: 2024-05-13 11:41
 */
@Data
public class SubjectMappingBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -34203260282791147L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 标签id
     */
    private Long labelId;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
