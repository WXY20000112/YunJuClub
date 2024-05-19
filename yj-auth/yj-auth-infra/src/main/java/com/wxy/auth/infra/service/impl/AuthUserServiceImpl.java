package com.wxy.auth.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.infra.entity.AuthUser;
import com.wxy.auth.infra.mapper.AuthUserMapper;
import com.wxy.auth.infra.service.AuthUserService;
import org.springframework.stereotype.Service;

import static com.wxy.auth.infra.entity.table.AuthUserTableDef.AUTH_USER;

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

    /**
     * @author: 32115
     * @description: 添加用户
     * @date: 2024/5/19
     * @param: authUser
     * @return: Boolean
     */
    @Override
    public Boolean addAuthUser(AuthUser authUser) {
        return this.save(authUser);
    }

    /**
     * @author: 32115
     * @description: 根据用户名查询用户是否存在
     * @date: 2024/5/19
     * @param: userName
     * @return: Boolean
     */
    @Override
    public Boolean existsAuthUserByUserName(String userName) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .from(AUTH_USER)
                .where(AUTH_USER.USER_NAME.eq(userName));
        return this.exists(queryWrapper);
    }
}
