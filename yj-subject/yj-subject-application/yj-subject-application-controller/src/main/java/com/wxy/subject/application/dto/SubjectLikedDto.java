package com.wxy.subject.application.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLiked实体类
 * @author: 王祥宇
 * @create: 2024-05-31 15:21:22
 */
@Data
public class SubjectLikedDto implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 857529680531734163L;
    
    /**
     * 主键
     */
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
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;

    /**
     * 第几页
     */
    private Integer pageNo;

    /**
     * 每页数据条数
     */
    private Integer pageSize;
}

