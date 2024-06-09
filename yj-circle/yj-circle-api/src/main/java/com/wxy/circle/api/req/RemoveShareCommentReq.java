package com.wxy.circle.api.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 32115
 * @description: 鸡圈内容信息
 * @date: 2024/6/9
 */
@Data
public class RemoveShareCommentReq implements Serializable {

    private Long id;

    /**
     * 回复类型 1评论 2回复
     */
    private Integer replyType;

}
