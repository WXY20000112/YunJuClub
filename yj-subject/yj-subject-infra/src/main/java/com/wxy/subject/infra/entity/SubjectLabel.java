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
 * @description: SubjectLabel实体类
 * @author: 32115
 * @create: 2024-05-13 11:40
 */
@Data
@Table(value = "subject_label",
        onInsert = MyInsertAndUpdateListener.class,
        onUpdate = MyInsertAndUpdateListener.class)
public class SubjectLabel implements Serializable {

    @Serial
    private static final long serialVersionUID = -40881299659446306L;

    /**
     * 主键
     */@Id(keyType = KeyType.None)

    private Long id;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 标签排序
     */
    private Integer labelSort;

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

    /**
     * 一级分类的id
     */
    private Long categoryId;
}
