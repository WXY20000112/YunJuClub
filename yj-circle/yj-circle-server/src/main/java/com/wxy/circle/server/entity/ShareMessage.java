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
 * @description: ShareMessage实体类
 * @author: 32115
 * @create: 2024-06-09 16:21
 */
@Data
@Table(value = "share_message",
        onUpdate = MyInsertAndUpdateListener.class,
        onInsert = MyInsertAndUpdateListener.class)
public class ShareMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -25704161859693458L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.None)
    private Integer id;

    /**
     * 来自人
     */
    private String fromId;

    /**
     * 送达人
     */
    private String toId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否被阅读 1是 2否
     */
    private Integer isRead;

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
}
