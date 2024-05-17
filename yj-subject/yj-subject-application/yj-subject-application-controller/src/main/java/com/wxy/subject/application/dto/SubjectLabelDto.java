package com.wxy.subject.application.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLabelBO实体类
 * @author: 32115
 * @create: 2024-05-13 11:40
 */
@Data
public class SubjectLabelDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -40881299659446306L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 标签排序
     */
    private Integer sortNum;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;

    /**
     * 一级分类的id
     */
    private Long categoryId;
}
