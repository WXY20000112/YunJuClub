package com.wxy.auth.domain.service.impl;

import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.domain.entity.AuthRolePermissionBO;
import com.wxy.auth.domain.service.AuthRolePermissionDomainService;
import com.wxy.auth.infra.entity.AuthRolePermission;
import com.wxy.auth.infra.service.AuthRolePermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRolePermissionDomainService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthRolePermissionDomainServiceImpl implements AuthRolePermissionDomainService {

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    /**
     * @author: 32115
     * @description: 添加角色与权限的关联关系
     * @date: 2024/5/19
     * @param: authRolePermissionBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean addAuthRolePermission(AuthRolePermissionBO authRolePermissionBO) {
        // 创建authRolePermissionList封装角色权限对应关系
        List<AuthRolePermission> authRolePermissionList = authRolePermissionBO.getPermissionIdList()
                .stream().map(permissionId -> {
                    AuthRolePermission authRolePermission = new AuthRolePermission();
                    // 设置角色id
                    authRolePermission.setRoleId(authRolePermissionBO.getRoleId());
                    // 设置权限id
                    authRolePermission.setPermissionId(permissionId);
                    return authRolePermission;
                }).toList();
        // 批量保存
        return authRolePermissionService.addAuthRolePermission(authRolePermissionList);
    }
}
