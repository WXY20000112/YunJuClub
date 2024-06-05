package com.wxy.practice.server.rpc.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategory实体类
 * @author: 32115
 * @create: 2024-05-13 11:31
 */
@Data
public class SubjectCategory implements Serializable {

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
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
