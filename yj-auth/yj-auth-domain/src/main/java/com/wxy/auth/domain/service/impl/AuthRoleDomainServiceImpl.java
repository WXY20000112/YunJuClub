package com.wxy.auth.domain.service.impl;

import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.domain.converter.AuthRoleBOConverter;
import com.wxy.auth.domain.entity.AuthRoleBO;
import com.wxy.auth.domain.service.AuthRoleDomainService;
import com.wxy.auth.infra.entity.AuthRole;
import com.wxy.auth.infra.service.AuthRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRoleDomainService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthRoleDomainServiceImpl implements AuthRoleDomainService {

    @Resource
    private AuthRoleService authRoleService;

    /**
     * @author: 32115
     * @description: 启用/禁用角色
     * @date: 2024/5/19
     * @param: authRoleBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean changeRoleStatus(AuthRoleBO authRoleBO) {
        // Bo 转 Entity
        AuthRole authRole = AuthRoleBOConverter
                .CONVERTER.converterBOToEntity(authRoleBO);
        // 启用/禁用并返回结果
        return authRoleService.changeAuthRoleStatus(authRole);
    }

    /**
     * @author: 32115
     * @description: 删除角色
     * @date: 2024/5/19
     * @param: authRoleBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean deleteAuthRole(AuthRoleBO authRoleBO) {
        // Bo 转 Entity
        AuthRole authRole = AuthRoleBOConverter
                .CONVERTER.converterBOToEntity(authRoleBO);
        // 删除并返回结果
        return authRoleService.deleteAuthRole(authRole);
    }

    /**
     * @author: 32115
     * @description: 更新角色
     * @date: 2024/5/19
     * @param: authRoleBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean updateAuthRole(AuthRoleBO authRoleBO) {
        // Bo 转 Entity
        AuthRole authRole = AuthRoleBOConverter
                .CONVERTER.converterBOToEntity(authRoleBO);
        // 更新并返回结果
        return authRoleService.updateAuthRole(authRole);
    }

    /**
     * @author: 32115
     * @description: 添加角色
     * @date: 2024/5/18
     * @param: authRoleBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean addRole(AuthRoleBO authRoleBO) {
        // Bo 转 Entity
        AuthRole authRole = AuthRoleBOConverter.CONVERTER.converterBOToEntity(authRoleBO);
        // 保存
        return authRoleService.addAuthRole(authRole);
    }
}
