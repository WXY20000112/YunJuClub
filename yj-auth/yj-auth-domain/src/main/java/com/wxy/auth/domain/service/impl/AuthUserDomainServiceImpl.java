package com.wxy.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.google.gson.Gson;
import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.common.constant.AuthConstant;
import com.wxy.auth.common.utils.RedisUtil;
import com.wxy.auth.domain.converter.AuthUserBOConverter;
import com.wxy.auth.domain.entity.AuthUserBO;
import com.wxy.auth.domain.service.AuthUserDomainService;
import com.wxy.auth.infra.entity.AuthPermission;
import com.wxy.auth.infra.entity.AuthRole;
import com.wxy.auth.infra.entity.AuthUser;
import com.wxy.auth.infra.entity.AuthUserRole;
import com.wxy.auth.infra.service.AuthPermissionService;
import com.wxy.auth.infra.service.AuthRoleService;
import com.wxy.auth.infra.service.AuthUserRoleService;
import com.wxy.auth.infra.service.AuthUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserDomainService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthUserDomainServiceImpl implements AuthUserDomainService {

    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AuthPermissionService authPermissionService;

    /**
     * @author: 32115
     * @description: 注册添加用户
     * @date: 2024/5/19
     * @param: authUserBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean register(AuthUserBO authUserBO) {
        // 1、判断用户是否存在
        if (authUserService.existsAuthUserByUserName(authUserBO.getUserName())) return true;
        // 2、初始化用户没有设置的信息
        // 2.1、设置初始昵称
        if (authUserBO.getNickName() == null) authUserBO.setNickName(AuthConstant.INITIAL_NICKNAME);
        // 2.2、设置初始头像
        if (authUserBO.getAvatar() == null) authUserBO.setAvatar(AuthConstant.INITIAL_AVATAR);
        // 3、对用户密码进行加密
        authUserBO.setPassword(SaSecureUtil
                .aesEncrypt(AuthConstant.ENCRYPT_KEY, authUserBO.getPassword()));
        // 4、BO转实体类
        AuthUser authUser = AuthUserBOConverter.CONVERTER
                .converterBOToEntity(authUserBO);
        // 5、添加用户
        Boolean resultAuthUser = authUserService.addAuthUser(authUser);

        // 用户注册时给新用户绑定一个初始角色normal_user
        // 1、获取初始角色id
        AuthRole authRole = authRoleService
                .getRoleByRoleKey(AuthConstant.NORMAL_USER);
        // 2、添加用户角色关联
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(authUser.getId());
        authUserRole.setRoleId(authRole.getId());
        Boolean resultAuthUserRole = authUserRoleService
                .addAuthUserRole(authUserRole);

        // 将用户初始角色及权限信息放入redis缓存
        // 1、使用redis工具类创建roleKey和permissionKey
        String roleKey = redisUtil.buildKey(
                AuthConstant.AUTH_ROLE_PREFIX, authUser.getUserName());
        String permissionKey = redisUtil.buildKey(
                AuthConstant.AUTH_PERMISSION_PREFIX, authUser.getUserName());
        // 2、将用户下的初始角色信息放入redis缓存 因为是新注册用户所以只有初始角色 不需要查询数据库
        List<AuthRole> authRoleList = new ArrayList<>();
        authRoleList.add(authRole);
        redisUtil.set(roleKey, new Gson().toJson(authRoleList));
        // 3、跟据角色id通过auth_role_permission表查询权限信息
        List<AuthPermission> authPermissionList = authPermissionService
                .getPermissionByRoleId(authRole.getId());
        // 4、将用户下的初始权限信息放入redis缓存
        redisUtil.set(permissionKey, new Gson().toJson(authPermissionList));
        return resultAuthUser && resultAuthUserRole;
    }
}
