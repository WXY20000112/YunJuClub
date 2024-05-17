package com.wxy.auth.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.auth.infra.entity.AuthPermission;

/**
 * @program: YunJuClub-Flex
 * @description: AuthPermissionService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthPermissionService extends IService<AuthPermission> {

    // 添加权限
    Boolean addPermission(AuthPermission authPermission);

    // 修改权限
    Boolean updateAuthPermission(AuthPermission authPermission);

    // 删除权限
    Boolean deleteAuthPermission(AuthPermission authPermission);

    // 修改权限状态
    Boolean changePermissionStatus(AuthPermission authPermission);

    // 更新权限菜单显示状态
    boolean changePermissionMenuShowStatus(AuthPermission authPermission);
}
