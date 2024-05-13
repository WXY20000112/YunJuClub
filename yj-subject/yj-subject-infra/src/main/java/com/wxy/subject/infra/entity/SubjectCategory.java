package com.wxy.subject.infra.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.wxy.subject.common.listener.MyInsertAndUpdateListener;
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
@Table(value = "subject_category",
        onInsert = MyInsertAndUpdateListener.class,
        onUpdate = MyInsertAndUpdateListener.class)
public class SubjectCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = -71389453845520999L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.None)
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
    private String categoryIcon;

    /**
     * 分类所属的父级id
     */
    private Long parentId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
