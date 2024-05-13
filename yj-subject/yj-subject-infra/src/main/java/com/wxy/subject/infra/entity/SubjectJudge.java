package com.wxy.subject.infra.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectJudge实体类
 * @author: 32115
 * @create: 2024-05-13 11:38
 */
@Data
public class SubjectJudge implements Serializable {
    @Serial
    private static final long serialVersionUID = -89009200151001273L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 是否正确
     */
    private Integer isCorrect;

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
