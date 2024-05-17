package com.wxy.auth.application.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRolePermissionBO实体类
 * @author: 32115
 * @create: 2024-05-17 14:18
 */
@Data
public class AuthRolePermissionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 787563252566244640L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;

    /**
     * 给角色赋予的权限id列表
     */
    private List<Long> permissionIdList;
}
