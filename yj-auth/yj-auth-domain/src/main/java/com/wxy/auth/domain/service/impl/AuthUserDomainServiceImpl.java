package com.wxy.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.gson.Gson;
import com.wxy.auth.api.entity.Result;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
@EnableAspectJAutoProxy(exposeProxy = true)
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
     * @description: 根据用户名批量获取用户信息
     * @date: 2024/6/9
     * @param: userNameList
     * @return: List<AuthUserBO>
     */
    @Override
    public List<AuthUserBO> getUserInfoListByUserName(List<String> userNameList) {
        // 获取用户信息
        List<AuthUser> authUserList =
                authUserService.getUserInfoListByUserName(userNameList);
        // Entity 转换成 Bo
        return AuthUserBOConverter.CONVERTER
                .converterEntityListToBoList(authUserList);
    }

    /**
     * @author: 32115
     * @description: 获取用户信息
     * @date: 2024/5/21
     * @param: authUserBO
     * @return: AuthUserBO
     */
    @Override
    @AopLogAnnotations
    public AuthUserBO getUserInfo(AuthUserBO authUserBO) {
        // Bo 转换成实体类 并根据用户名查询用户信息
        AuthUser authUser = authUserService.getUserInfoByUserName(
                AuthUserBOConverter.CONVERTER.converterBOToEntity(authUserBO));
        // 判断用户是否存在
        if (authUser == null) return new AuthUserBO();
        return AuthUserBOConverter
                .CONVERTER.converterEntityToBo(authUser);
    }

    /**
     * @author: 32115
     * @description: 根据用户传递的验证码进行登录
     * @date: 2024/5/21
     * @param: code
     * @return: Result<SaTokenInfo>
     */
    @Override
    @AopLogAnnotations
    public Result<SaTokenInfo> login(String code) {
        // 创建codeKey 用于在redis缓存中获取用户登录信息
        String codeKey = redisUtil.buildKey(AuthConstant.WECHAT_CODE_PREFIX, code);
        // 根据code在缓存中拿到用户openId
        String openId = redisUtil.get(codeKey);
        // 若id为空说明验证码已过期或者非法获取的验证码 直接返回错误信息
        if (StringUtils.isBlank(openId)) {
            return Result.error("验证码已失效，请重新获取");
        }
        // 若不为空 就将当前扫码用户进行注册 在register方法中有用户唯一性校验 所以再此不必进行校验
        // 设置用户初始信息
        AuthUserBO authUserBo = new AuthUserBO();
        authUserBo.setUserName(openId);
        authUserBo.setPassword(AuthConstant.INITIAL_PASSWORD);
        authUserBo.setEmail(AuthConstant.INITIAL_EMAIL);
        authUserBo.setPhone(AuthConstant.INITIAL_PHONE);
        authUserBo.setSex(AuthConstant.INITIAL_SEX);
        // 使用上下文工具类获取当前对象的代理类@EnableAspectJAutoProxy (exposeProxy = true)
        // 然后通过下面方法获取代理对象，然后再调用 可以避免方法自调用造成的Transactional事务失效
        AuthUserDomainServiceImpl proxy = (AuthUserDomainServiceImpl) AopContext.currentProxy();
        // 调用注册方法进行用户信息初始化
        proxy.register(authUserBo);
        // 用户进行登录并返回token信息
        StpUtil.login(openId);
        return Result.success(StpUtil.getTokenInfo());
    }

    /**
     * @author: 32115
     * @description: 更改用户状态
     * @date: 2024/5/21
     * @param: authUserBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean changeAuthUserStatus(AuthUserBO authUserBO) {
        // Bo 转 实体类
        AuthUser authUser = AuthUserBOConverter
                .CONVERTER.converterBOToEntity(authUserBO);
        // 更改用户状态
        return authUserService.changeAuthUserStatus(authUser);
    }

    /**
     * @author: 32115
     * @description: 删除用户
     * @date: 2024/5/21
     * @param: authUserBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean deleteAuthUser(AuthUserBO authUserBO) {
        // Bo 转 实体类
        AuthUser authUser = AuthUserBOConverter
                .CONVERTER.converterBOToEntity(authUserBO);
        // 删除用户
        return authUserService.deleteAuthUser(authUser);
    }

    /**
     * @author: 32115
     * @description: 更新用户信息
     * @date: 2024/5/21
     * @param: authUserBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean updateUserInfo(AuthUserBO authUserBO) {
        // 修改之前要先确认用户密码是否进行了修该，即密码是否为空 不为则设置了新密码，就要对新密码重新加密存储
        if (authUserBO.getPassword() != null) {
            // 1、对用户密码进行加密
            authUserBO.setPassword(SaSecureUtil
                    .aesEncrypt(AuthConstant.ENCRYPT_KEY, authUserBO.getPassword()));
        }
        // 2、BO转实体类
        AuthUser authUser = AuthUserBOConverter
                .CONVERTER.converterBOToEntity(authUserBO);
        // 3、更新用户信息
        return authUserService.updateAuthUser(authUser);
    }

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
