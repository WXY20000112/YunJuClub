package com.wxy.auth.domain.service;

import com.wxy.auth.domain.entity.AuthUserRoleBO;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRoleDomainService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthUserRoleDomainService {

    // 添加用户角色关联关系
    Boolean addAuthUserRole(AuthUserRoleBO authUserRoleBO);
}
