package com.wxy.auth.domain.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.wxy.auth.api.entity.Result;
import com.wxy.auth.domain.entity.AuthUserBO;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserDomainService
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
public interface AuthUserDomainService {

    // 注册添加用户
    Boolean register(AuthUserBO authUserBO);

    // 更新用户信息
    Boolean updateUserInfo(AuthUserBO authUserBO);

    // 删除用户
    Boolean deleteAuthUser(AuthUserBO authUserBO);

    // 更改用户状态
    Boolean changeAuthUserStatus(AuthUserBO authUserBO);

    // 根据用户传递的验证码进行登录
    Result<SaTokenInfo> login(String code);

    // 获取用户信息
    AuthUserBO getUserInfo(AuthUserBO authUserBO);
}
