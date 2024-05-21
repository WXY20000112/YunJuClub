package com.wxy.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxy.gateway.constant.GatewayConstant;
import com.wxy.gateway.entity.AuthPermission;
import com.wxy.gateway.entity.AuthRole;
import com.wxy.gateway.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义权限验证接口扩展 
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return getAuthList(GatewayConstant.AUTH_PERMISSION_PREFIX, loginId);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return getAuthList(GatewayConstant.AUTH_ROLE_PREFIX, loginId);
    }

    private List<String> getAuthList(String prefix, Object loginId) {
        // 拼接出authKey
        String authKey = redisUtil.buildKey(prefix, loginId.toString());
        // 获取value
        String authValue = redisUtil.get(authKey);
        // 如果value为空 返回空列表即可
        if (StringUtils.isBlank(authValue)){
            return Collections.emptyList();
        }
        // 存储要返回的角色或者权限列表
        List<String> authList = new ArrayList<>();
        // 使用gson工具将结果序列化为list集合
        if (GatewayConstant.AUTH_ROLE_PREFIX.equals(prefix)){
            // 如果是获取角色列表 使用下面方法进行gson转换
            List<AuthRole> authRoleList = new Gson()
                    .fromJson(authValue, new TypeToken<List<AuthRole>>(){}.getType());
            authList = authRoleList.stream()
                    .map(AuthRole::getRoleKey).toList();
        }else if (GatewayConstant.AUTH_PERMISSION_PREFIX.equals(prefix)){
            // 如果是获取权限列表 使用下面方法进行gson转换
            List<AuthPermission> authPermissionList = new Gson()
                    .fromJson(authValue, new TypeToken<List<AuthPermission>>(){}.getType());
            authList = authPermissionList.stream()
                    .map(AuthPermission::getPermissionKey).toList();
        }
        return authList;
    }

}
