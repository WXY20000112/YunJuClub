package com.wxy.auth.infra.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.infra.entity.AuthRole;
import com.wxy.auth.infra.mapper.AuthRoleMapper;
import com.wxy.auth.infra.service.AuthRoleService;
import org.springframework.stereotype.Service;

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
}
