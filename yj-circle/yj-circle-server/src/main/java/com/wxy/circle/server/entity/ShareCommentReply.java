package com.wxy.circle.server.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.wxy.circle.server.listener.MyInsertAndUpdateListener;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: YunJuClub-Flex
 * @description: ShareCommentReply实体类
 * @author: 32115
 * @create: 2024-06-09 16:19
 */
@Data
@Table(value = "share_comment_reply",
        onUpdate = MyInsertAndUpdateListener.class,
        onInsert = MyInsertAndUpdateListener.class)
public class ShareCommentReply implements Serializable {

    @Serial
    private static final long serialVersionUID = 179082276200522947L;

    /**
     * 评论ID
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 原始动态ID
     */
    private Integer momentId;

    /**
     * 回复类型 1评论 2回复
     */
    private Integer replyType;

    /**
     * 评论目标id
     */
    private Long toId;

    /**
     * 评论人
     */
    private String toUser;

    /**
     * 评论人是否作者 1=是 0=否
     */
    private Integer toUserAuthor;

    /**
     * 回复目标id
     */
    private Long replyId;

    /**
     * 回复人
     */
    private String replyUser;

    /**
     * 回复人是否作者 1=是 0=否
     */
    private Integer replayAuthor;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片内容
     */
    private String picUrls;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否被删除 0为删除 1已删除
     */
    private Integer isDeleted;


    private Long parentId;
}
