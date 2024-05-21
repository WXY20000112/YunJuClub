package com.wxy.gateway.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRole实体类
 * @author: 32115
 * @create: 2024-05-17 14:17
 */
@Data
public class AuthRole implements Serializable {

    @Serial
    private static final long serialVersionUID = -48884489779964726L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态 0为正常 1为禁用
     */
    private Integer roleStatus;

    /**
     * 角色唯一标识
     */
    private String roleKey;

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
     * 修改人
     */
    private Date updateTime;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
