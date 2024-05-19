package com.wxy.auth.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.infra.entity.AuthPermission;
import com.wxy.auth.infra.mapper.AuthPermissionMapper;
import com.wxy.auth.infra.service.AuthPermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wxy.auth.infra.entity.table.AuthPermissionTableDef.AUTH_PERMISSION;
import static com.wxy.auth.infra.entity.table.AuthRolePermissionTableDef.AUTH_ROLE_PERMISSION;
import static com.wxy.auth.infra.entity.table.AuthRoleTableDef.AUTH_ROLE;

/**
 * @program: YunJuClub-Flex
 * @description: AuthPermissionService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthPermissionServiceImpl
        extends ServiceImpl<AuthPermissionMapper, AuthPermission>
        implements AuthPermissionService {

    @Resource
    private AuthPermissionMapper authPermissionMapper;

    /**
     * @author: 32115
     * @description: 根据角色id获取权限列表
     * @date: 2024/5/19
     * @param: id
     * @return: List<AuthPermission>
     */
    @Override
    public List<AuthPermission> getPermissionByRoleId(Long id) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(AUTH_PERMISSION.DEFAULT_COLUMNS)
                .from(AUTH_ROLE)
                .leftJoin(AUTH_ROLE_PERMISSION).on(AUTH_ROLE.ID.eq(AUTH_ROLE_PERMISSION.ROLE_ID))
                .leftJoin(AUTH_PERMISSION).on(AUTH_PERMISSION.ID.eq(AUTH_ROLE_PERMISSION.PERMISSION_ID))
                .where(AUTH_ROLE.ID.eq(id));
        return this.list(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 更新权限菜单显示状态
     * @date: 2024/5/17
     * @param: authPermission
     * @return: boolean
     */
    @Override
    public boolean changePermissionMenuShowStatus(AuthPermission authPermission) {
        return UpdateChain.of(AuthPermission.class)
                .setRaw(AuthPermission::getShow, "1 - `show`")
                .where(AuthPermission::getId).eq(authPermission.getId())
                .update();
    }

    /**
     * @author: 32115
     * @description: 更改权限状态
     * @date: 2024/5/17
     * @param: authPermission
     * @return: Boolean
     */
    @Override
    public Boolean changePermissionStatus(AuthPermission authPermission) {
        return UpdateChain.of(AuthPermission.class)
                .setRaw(AuthPermission::getStatus, "1 - status")
                .where(AuthPermission::getId).eq(authPermission.getId())
                .update();
    }

    /**
     * @author: 32115
     * @description: 删除权限
     * @date: 2024/5/17
     * @param: authPermission
     * @return: Boolean
     */
    @Override
    public Boolean deleteAuthPermission(AuthPermission authPermission) {
        return authPermissionMapper.delete(authPermission) > 0;
    }

    /**
     * @author: 32115
     * @description: 更新权限
     * @date: 2024/5/17
     * @param: authPermission
     * @return: Boolean
     */
    @Override
    public Boolean updateAuthPermission(AuthPermission authPermission) {
        return this.updateById(authPermission);
    }

    /**
     * @author: 32115
     * @description: 添加权限
     * @date: 2024/5/17
     * @param: authPermission
     * @return: Boolean
     */
    @Override
    public Boolean addPermission(AuthPermission authPermission) {
        return this.save(authPermission);
    }
}
