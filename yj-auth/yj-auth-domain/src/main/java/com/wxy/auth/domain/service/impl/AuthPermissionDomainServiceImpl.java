package com.wxy.auth.domain.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxy.auth.common.constant.AuthConstant;
import com.wxy.auth.common.utils.RedisUtil;
import com.wxy.auth.domain.converter.AuthPermissionBOConverter;
import com.wxy.auth.domain.entity.AuthPermissionBO;
import com.wxy.auth.domain.service.AuthPermissionDomainService;
import com.wxy.auth.infra.entity.AuthPermission;
import com.wxy.auth.infra.service.AuthPermissionService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthPermissionDomainService实现类
 * @author: 32115
 * @create: 2024-05-17 14:34
 */
@Service
public class AuthPermissionDomainServiceImpl implements AuthPermissionDomainService {

    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * @author: 32115
     * @description: 根据用户名获取权限列表
     * @date: 2024/5/17
     * @param: userName
     * @return: List<String>
     */
    @Override
    public List<String> getPermissionList(String userName) {
        // 创建key
        String permissionKey = redisUtil.buildKey(AuthConstant.AUTH_PERMISSION_PREFIX, userName);
        // 从redis中获取权限列表
        String permissionValue = redisUtil.get(permissionKey);
        if (StringUtils.isEmpty(permissionValue)) {
            return Collections.emptyList();
        }
        // 将权限列表序列化为List<String>
        List<AuthPermission> authPermissionList = new Gson()
                .fromJson(permissionValue, new TypeToken<List<AuthPermission>>() {}.getType());
        // 提取权限标识并返回
        return authPermissionList.stream().map(AuthPermission::getPermissionKey).toList();
    }

    /**
     * @author: 32115
     * @description: 更新权限菜单显示状态
     * @date: 2024/5/17
     * @param: authPermissionBO
     * @return: boolean
     */
    @Override
    public Boolean changePermissionMenuShowStatus(AuthPermissionBO authPermissionBO) {
        // BO 转成 实体类
        AuthPermission authPermission = AuthPermissionBOConverter
                .CONVERTER.converterBOToEntity(authPermissionBO);
        // 调用service更改并返回结果
        return authPermissionService.changePermissionMenuShowStatus(authPermission);
    }

    /**
     * @author: 32115
     * @description: 更改权限状态
     * @date: 2024/5/17
     * @param: authPermissionBO
     * @return: Boolean
     */
    @Override
    public Boolean changePermissionStatus(AuthPermissionBO authPermissionBO) {
        // BO 转成 实体类
        AuthPermission authPermission = AuthPermissionBOConverter
                .CONVERTER.converterBOToEntity(authPermissionBO);
        // 调用service更改并返回结果
        return authPermissionService.changePermissionStatus(authPermission);
    }

    /**
     * @author: 32115
     * @description: 删除权限
     * @date: 2024/5/17
     * @param: authPermissionBO
     * @return: Boolean
     */
    @Override
    public Boolean deleteAuthPermission(AuthPermissionBO authPermissionBO) {
        // BO 转成 实体类
        AuthPermission authPermission = AuthPermissionBOConverter
                .CONVERTER.converterBOToEntity(authPermissionBO);
        // 调用service删除并返回结果
        return authPermissionService.deleteAuthPermission(authPermission);
    }

    /**
     * @author: 32115
     * @description: 更新权限
     * @date: 2024/5/17
     * @param: authPermissionBO
     * @return: Boolean
     */
    @Override
    public Boolean updateAuthPermission(AuthPermissionBO authPermissionBO) {
        // Bo 转换成 实体类
        AuthPermission authPermission = AuthPermissionBOConverter
                .CONVERTER.converterBOToEntity(authPermissionBO);
        // 调用service更新并返回结果
        return authPermissionService.updateAuthPermission(authPermission);
    }

    /**
     * @author: 32115
     * @description: 添加权限
     * @date: 2024/5/17
     * @param: authPermissionBO
     * @return: Boolean
     */
    @Override
    @Transactional
    public Boolean addPermission(AuthPermissionBO authPermissionBO) {
        // Bo 转换成 实体类
        AuthPermission authPermission = AuthPermissionBOConverter
                .CONVERTER.converterBOToEntity(authPermissionBO);
        // 调用service添加并返回结果
        return authPermissionService.addPermission(authPermission);
    }
}
