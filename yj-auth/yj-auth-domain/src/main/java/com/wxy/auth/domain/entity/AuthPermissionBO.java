package com.wxy.auth.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: AuthPermissionBO实体类
 * @author: 32115
 * @create: 2024-05-17 14:12
 */
@Data
public class AuthPermissionBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -29083172075947504L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 父权限id
     */
    private Long parentId;

    /**
     * 权限类型 0为菜单权限 1为操作权限
     */
    private Integer type;

    /**
     * 菜单路由
     */
    private String menuUrl;

    /**
     * 权限状态 0为正常 1为禁用
     */
    private Integer status;

    /**
     * 菜单展示状态 0为展示 1为隐藏
     */
    private Integer show;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 权限唯一标识
     */
    private String permissionKey;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
