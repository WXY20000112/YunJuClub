package com.wxy.auth.domain.service;

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
}
