package com.wxy.auth.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.infra.entity.AuthRole;
import com.wxy.auth.infra.mapper.AuthRoleMapper;
import com.wxy.auth.infra.service.AuthRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static com.wxy.auth.infra.entity.table.AuthRoleTableDef.AUTH_ROLE;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRoleService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthRoleServiceImpl
        extends ServiceImpl<AuthRoleMapper, AuthRole>
        implements AuthRoleService {

    @Resource
    private AuthRoleMapper authRoleMapper;

    /**
     * @author: 32115
     * @description: 根据角色Key获取角色
     * @date: 2024/5/19
     * @param: normalUser
     * @return: AuthRole
     */
    @Override
    public AuthRole getRoleByRoleKey(String normalUser) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(AUTH_ROLE.DEFAULT_COLUMNS)
                .from(AUTH_ROLE)
                .where(AUTH_ROLE.ROLE_KEY.eq(normalUser));
        return this.getOne(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 启用/禁用角色
     * @date: 2024/5/19
     * @param: authRole
     * @return: Boolean
     */
    @Override
    public Boolean changeAuthRoleStatus(AuthRole authRole) {
        return UpdateChain.of(AuthRole.class)
                .setRaw("role_status", "1 - `role_status`")
                .where(AuthRole::getId).eq(authRole.getId())
                .update();
    }

    /**
     * @author: 32115
     * @description: 删除角色
     * @date: 2024/5/19
     * @param: authRole
     * @return: Boolean
     */
    @Override
    public Boolean deleteAuthRole(AuthRole authRole) {
        return authRoleMapper.delete(authRole) > 0;
    }

    /**
     * @author: 32115
     * @description: 更新角色
     * @date: 2024/5/19
     * @param: authRole
     * @return: Boolean
     */
    @Override
    public Boolean updateAuthRole(AuthRole authRole) {
        return this.updateById(authRole);
    }

    /**
     * @author: 32115
     * @description: 添加角色
     * @date: 2024/5/18
     * @param: authRole
     * @return: Boolean
     */
    @Override
    public Boolean addAuthRole(AuthRole authRole) {
        return this.save(authRole);
    }
}
