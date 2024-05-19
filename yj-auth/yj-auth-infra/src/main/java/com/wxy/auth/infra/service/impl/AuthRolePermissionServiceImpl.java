package com.wxy.auth.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.infra.entity.AuthRolePermission;
import com.wxy.auth.infra.mapper.AuthRolePermissionMapper;
import com.wxy.auth.infra.service.AuthRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRolePermissionService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthRolePermissionServiceImpl
        extends ServiceImpl<AuthRolePermissionMapper, AuthRolePermission>
        implements AuthRolePermissionService {

    /**
     * @author: 32115
     * @description: 添加角色与权限的关联关系
     * @date: 2024/5/19
     * @param: authRolePermissionList
     * @return: Boolean
     */
    @Override
    public Boolean addAuthRolePermission(List<AuthRolePermission> authRolePermissionList) {
        return this.saveBatch(authRolePermissionList);
    }
}
