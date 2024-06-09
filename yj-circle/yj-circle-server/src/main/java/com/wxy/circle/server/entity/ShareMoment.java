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
 * @description: ShareMoment实体类
 * @author: 32115
 * @create: 2024-06-09 16:22
 */
@Data
@Table(value = "share_moment",
        onUpdate = MyInsertAndUpdateListener.class,
        onInsert = MyInsertAndUpdateListener.class)
public class ShareMoment implements Serializable {

    @Serial
    private static final long serialVersionUID = -13321651080016940L;

    /**
     * 动态ID
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 圈子ID
     */
    private Long circleId;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 动态图片内容
     */
    private String picUrls;

    /**
     * 回复数
     */
    private Integer replyCount;

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
