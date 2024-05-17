package com.wxy.auth.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.infra.entity.AuthUser;
import com.wxy.auth.infra.mapper.AuthUserMapper;
import com.wxy.auth.infra.service.AuthUserService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthUserServiceImpl
        extends ServiceImpl<AuthUserMapper, AuthUser>
        implements AuthUserService {
}
