package com.wxy.auth.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.infra.entity.AuthUserRole;
import com.wxy.auth.infra.mapper.AuthUserRoleMapper;
import com.wxy.auth.infra.service.AuthUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRoleService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthUserRoleServiceImpl
        extends ServiceImpl<AuthUserRoleMapper, AuthUserRole>
        implements AuthUserRoleService {

    /**
     * @author: 32115
     * @description: 添加用户角色关联关系
     * @date: 2024/5/19
     * @param: authUserRole
     * @return: Boolean
     */
    @Override
    public Boolean addAuthUserRole(AuthUserRole authUserRole) {
        return this.save(authUserRole);
    }
}
