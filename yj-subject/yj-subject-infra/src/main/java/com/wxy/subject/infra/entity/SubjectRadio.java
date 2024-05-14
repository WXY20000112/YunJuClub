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
 * @description: SubjectRadio实体类
 * @author: 32115
 * @create: 2024-05-13 11:44
 */
@Data
@Table(value = "subject_radio",
        onInsert = MyInsertAndUpdateListener.class,
        onUpdate = MyInsertAndUpdateListener.class)
public class SubjectRadio implements Serializable {

    @Serial
    private static final long serialVersionUID = -85190502716419235L;

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
