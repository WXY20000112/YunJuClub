package com.wxy.auth.infra.service.impl;

import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.infra.entity.AuthPermission;
import com.wxy.auth.infra.mapper.AuthPermissionMapper;
import com.wxy.auth.infra.service.AuthPermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
