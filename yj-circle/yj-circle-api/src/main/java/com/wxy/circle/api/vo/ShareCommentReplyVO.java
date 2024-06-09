package com.wxy.circle.api.vo;

import com.wxy.circle.api.common.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author: 32115
 * @description: 评论及回复信息
 * @date: 2024/6/9
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShareCommentReplyVO extends TreeNode implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 原始动态ID
     */
    private Long momentId;

    /**
     * 回复类型 1评论 2回复
     */
    private Integer replyType;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片内容
     */
    private List<String> picUrlList;

    private String fromId;

    private String toId;

    private Long parentId;

    private String userName;

    private String avatar;

    private long createdTime;

    @Override
    public Long getNodeId() {
        return id;
    }

    @Override
    public Long getNodePId() {
        return parentId;
    }

}
