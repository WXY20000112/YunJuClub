package com.wxy.subject.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategoryBO实体类
 * @author: 32115
 * @create: 2024-05-13 11:31
 */
@Data
public class SubjectCategoryBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -71389453845520999L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 分类名称 1为一级分类 2为二级分类
     */
    private String categoryName;

    /**
     * 分类类型
     */
    private Integer categoryType;

    /**
     * 分类图标
     */
    private String imageUrl;

    /**
     * 分类所属的父级id
     */
    private Long parentId;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;

    /**
     * 分类下二级分类数量
     */
    private Integer count;

    /**
     * 分类下标签信息
     */
    private List<SubjectLabelBO> subjectLabelBOList;
}
