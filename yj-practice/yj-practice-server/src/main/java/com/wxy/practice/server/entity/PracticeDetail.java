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
 * @description: PracticeDetail实体类
 * @author: 32115
 * @create: 2024-06-05 15:15
 */
@Data
@Table(value = "practice_detail",
        onInsert = MyInsertAndUpdateListener.class,
        onUpdate = MyInsertAndUpdateListener.class)
public class PracticeDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = -41037746881974732L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 套卷练习情况表的id
     */
    private Long practiceId;

    /**
     * 练习的题目的id
     */
    private Long subjectId;

    /**
     * 题目类型
     */
    private Integer subjectType;

    /**
     * 题目是否已作答 1为已做答 2为未作答
     */
    private Integer answerStatus;

    /**
     * 作答的内容
     */
    private String answerContent;

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
