package com.wxy.practice.server.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.wxy.practice.server.listener.MyInsertAndUpdateListener;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeInfo实体类
 * @author: 32115
 * @create: 2024-06-05 16:06
 */
@Data
@Table(value = "practice_info",
            onInsert = MyInsertAndUpdateListener.class,
            onUpdate = MyInsertAndUpdateListener.class)
public class PracticeInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 521099512850265662L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.None)
    private Long id;

    /**
     * 套卷的id
     */
    private Long setId;

    /**
     * 套卷是否完成 1为已完成 0为未完成
     */
    private Integer completeStatus;

    /**
     * 答题用时
     */
    private String timeUse;

    /**
     * 交卷时间
     */
    private Date submitTime;

    /**
     * 正确率
     */
    private BigDecimal correctRate;

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
