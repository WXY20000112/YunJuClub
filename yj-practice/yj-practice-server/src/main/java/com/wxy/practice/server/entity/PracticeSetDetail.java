package com.wxy.practice.server.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.wxy.practice.server.listener.MyInsertAndUpdateListener;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSetDetail
 * @author: 32115
 * @create: 2024-06-05 10:42
 */
@Data
@Table(value = "practice_set_detail",
        onInsert = MyInsertAndUpdateListener.class,
        onUpdate = MyInsertAndUpdateListener.class)
public class PracticeSetDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 506640040686161140L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 套卷的id
     */
    private Long setId;

    /**
     * 题目的id
     */
    private Long subjectId;

    /**
     * 题目的类型
     */
    private Integer subjectType;

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
