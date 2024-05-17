package com.wxy.auth.domain.service;

import com.wxy.auth.domain.entity.AuthPermissionBO;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthPermissionDomainService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthPermissionDomainService {

    // 添加权限
    Boolean addPermission(AuthPermissionBO authPermissionBO);

    // 修改权限
    Boolean updateAuthPermission(AuthPermissionBO authPermissionBO);

    // 删除权限
    Boolean deleteAuthPermission(AuthPermissionBO authPermissionBO);

    // 修改权限状态
    Boolean changePermissionStatus(AuthPermissionBO authPermissionBO);

    // 更新权限菜单显示状态
    Boolean changePermissionMenuShowStatus(AuthPermissionBO authPermissionBO);

    // 根据用户名获取权限列表
    List<String> getPermissionList(String userName);
}
