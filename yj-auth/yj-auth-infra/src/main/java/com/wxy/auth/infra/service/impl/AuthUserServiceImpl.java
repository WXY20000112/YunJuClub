package com.wxy.auth.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.infra.entity.AuthUser;
import com.wxy.auth.infra.mapper.AuthUserMapper;
import com.wxy.auth.infra.service.AuthUserService;
import jakarta.annotation.Resource;
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

    @Resource
    private AuthUserMapper authUserMapper;

    /**
     * @author: 32115
     * @description: 根据用户名获取用户信息
     * @date: 2024/5/21
     * @param: authUser
     * @return: AuthUser
     */
    @Override
    public AuthUser getUserInfoByUserName(AuthUser authUser) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(AUTH_USER.DEFAULT_COLUMNS)
                .from(AUTH_USER)
                .where(AUTH_USER.USER_NAME.eq(authUser.getUserName()));
        return this.getOne(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 更改用户状态
     * @date: 2024/5/21
     * @param: authUser
     * @return: Boolean
     */
    @Override
    public Boolean changeAuthUserStatus(AuthUser authUser) {
        return UpdateChain.of(AuthUser.class)
                .setRaw("status", "1 - `status`")
                .where(AuthUser::getId).eq(authUser.getId())
                .update();
    }

    /**
     * @author: 32115
     * @description: 删除用户
     * @date: 2024/5/21
     * @param: authUser
     * @return: Boolean
     */
    @Override
    public Boolean deleteAuthUser(AuthUser authUser) {
        return authUserMapper.delete(authUser) > 0;
    }

    /**
     * @author: 32115
     * @description: 更新用户
     * @date: 2024/5/21
     * @param: authUser
     * @return: Boolean
     */
    @Override
    public Boolean updateAuthUser(AuthUser authUser) {
        return this.updateById(authUser);
    }

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
