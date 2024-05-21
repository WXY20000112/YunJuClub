package com.wxy.auth.domain.service.impl;

import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.domain.entity.AuthUserRoleBO;
import com.wxy.auth.domain.service.AuthUserRoleDomainService;
import com.wxy.auth.infra.entity.AuthUserRole;
import com.wxy.auth.infra.service.AuthUserRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRoleDomainService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthUserRoleDomainServiceImpl implements AuthUserRoleDomainService {

    @Resource
    private AuthUserRoleService authUserRoleService;

    /**
     * @author: 32115
     * @description: 添加用户角色关联关系
     * @date: 2024/5/21
     * @param: authUserRoleBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean addAuthUserRole(AuthUserRoleBO authUserRoleBO) {
        // 创建AuthUserRoleList封装用户角色对应关系
        List<AuthUserRole> authUserRoleList = authUserRoleBO.getRoleIdList()
                .stream().map(roleId -> {
                    // 封装用户id与角色id对应关系
                    AuthUserRole authUserRole = new AuthUserRole();
                    // 设置用户id
                    authUserRole.setUserId(authUserRoleBO.getUserId());
                    // 设置角色id
                    authUserRole.setRoleId(roleId);
                    return authUserRole;
                }).toList();
        // 批量添加用户角色对应关系
        return authUserRoleService.addAuthUserRoleBatch(authUserRoleList);
    }
}
