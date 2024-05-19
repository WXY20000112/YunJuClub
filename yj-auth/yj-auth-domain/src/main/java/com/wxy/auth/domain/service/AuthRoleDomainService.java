package com.wxy.auth.domain.service;

import com.wxy.auth.domain.entity.AuthRoleBO;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRoleDomainService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthRoleDomainService {

    // 添加角色
    Boolean addRole(AuthRoleBO authRoleBO);

    // 修改角色
    Boolean updateAuthRole(AuthRoleBO authRoleBO);

    Boolean deleteAuthRole(AuthRoleBO authRoleBO);

    // 启用/禁用角色
    Boolean changeRoleStatus(AuthRoleBO authRoleBO);
}
