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
 * @description: SubjectLiked实体类
 * @author: 王祥宇
 * @create: 2024-05-31 15:21:22
 */
@Data
@Table(value = "subject_liked",
        onInsert = MyInsertAndUpdateListener.class,
        onUpdate = MyInsertAndUpdateListener.class)
public class SubjectLiked implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 857529680531734163L;
    
    /**
     * 主键
     */
    @Id(keyType = KeyType.None)
    private Long id;
    
    /**
     * 点赞的题目id
     */
    private Long subjectId;
    
    /**
     * 点赞人的id
     */
    private String likeUserId;
    
    /**
     * 是否点赞 0为未点赞，1为已点赞
     */
    private Integer status;
    
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

