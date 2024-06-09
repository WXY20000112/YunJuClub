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
 * @description: ShareCircle实体类
 * @author: 32115
 * @create: 2024-06-09 16:18
 */
@Data
@Table(value = "share_circle",
        onUpdate = MyInsertAndUpdateListener.class,
        onInsert = MyInsertAndUpdateListener.class)
public class ShareCircle implements Serializable {

    @Serial
    private static final long serialVersionUID = -14984114657518842L;

    /**
     * 圈子ID
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 父级ID,-1为大类
     */
    private Long parentId;

    /**
     * 圈子名称
     */
    private String circleName;

    /**
     * 圈子图片
     */
    private String icon;

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
