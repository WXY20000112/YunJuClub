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
 * @description: SubjectBrief实体类
 * @author: 32115
 * @create: 2024-05-13 11:28
 */
@Data
@Table(value = "subject_brief",
        onInsert = MyInsertAndUpdateListener.class,
        onUpdate = MyInsertAndUpdateListener.class)
public class SubjectBrief implements Serializable {

    @Serial
    private static final long serialVersionUID = -76141031605368189L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 题目答案
     */
    private String subjectAnswer;

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
