package com.wxy.auth.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.auth.infra.entity.AuthUserRole;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRoleService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthUserRoleService extends IService<AuthUserRole> {

    // 添加用户角色关联关系
    Boolean addAuthUserRole(AuthUserRole authUserRole);
}
