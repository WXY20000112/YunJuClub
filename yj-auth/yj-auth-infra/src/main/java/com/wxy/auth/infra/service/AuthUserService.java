package com.wxy.auth.infra.service;

import com.mybatisflex.core.service.IService;
import com.wxy.auth.infra.entity.AuthUser;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthUserService extends IService<AuthUser> {

    // 根据用户名查询用户是否存在
    Boolean existsAuthUserByUserName(String userName);

    // 添加用户
    Boolean addAuthUser(AuthUser authUser);
}
