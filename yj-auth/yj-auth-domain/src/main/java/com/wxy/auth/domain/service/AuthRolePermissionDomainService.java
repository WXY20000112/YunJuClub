package com.wxy.auth.domain.service;

import com.wxy.auth.domain.entity.AuthRolePermissionBO;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRolePermissionDomainService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthRolePermissionDomainService {

    // 添加角色与权限的关联关系
    Boolean addAuthRolePermission(AuthRolePermissionBO authRolePermissionBO);
}
