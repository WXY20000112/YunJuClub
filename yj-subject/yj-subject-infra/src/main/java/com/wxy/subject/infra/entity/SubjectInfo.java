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
@Table(value = "subject_info",
        onInsert = MyInsertAndUpdateListener.class,
        onUpdate = MyInsertAndUpdateListener.class)
public class SubjectInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 649887552684579511L;
    /**
     * 主键
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 题目名称
     */
    private String subjectName;

    /**
     * 题目难易度
     */
    private Integer subjectDifficult;

    /**
     * 出题人
     */
    private String settleName;

    /**
     * 题目类型 1单选 2多选 3判断 4简答
     */
    private Integer subjectType;

    /**
     * 题目分数
     */
    private Integer subjectScore;

    /**
     * 题目解析
     */
    private String subjectParse;

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

