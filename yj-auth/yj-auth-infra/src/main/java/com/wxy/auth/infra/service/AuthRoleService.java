package com.wxy.auth.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.auth.infra.entity.AuthRole;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRoleService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthRoleService extends IService<AuthRole> {

    // 添加角色
    Boolean addAuthRole(AuthRole authRole);

    // 修改角色
    Boolean updateAuthRole(AuthRole authRole);

    // 删除角色
    Boolean deleteAuthRole(AuthRole authRole);

    // 启用/禁用角色
    Boolean changeAuthRoleStatus(AuthRole authRole);

    // 根据角色key获取角色信息
    AuthRole getRoleByRoleKey(String normalUser);
}
