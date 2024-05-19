package com.wxy.auth.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.auth.infra.entity.AuthRolePermission;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRolePermissionService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthRolePermissionService extends IService<AuthRolePermission> {

    // 添加角色与权限的关联关系
    Boolean addAuthRolePermission(List<AuthRolePermission> authRolePermissionList);
}
