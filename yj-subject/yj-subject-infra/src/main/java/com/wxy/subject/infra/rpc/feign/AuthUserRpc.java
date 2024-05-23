package com.wxy.subject.infra.rpc.feign;

import com.wxy.auth.api.entity.AuthUserDto;
import com.wxy.auth.api.entity.Result;
import com.wxy.auth.api.feign.AuthUserFeignService;
import com.wxy.subject.infra.rpc.entity.AuthUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRpc
 * @author: 32115
 * @create: 2024-05-23 18:15
 */
@Component
public class AuthUserRpc {

    @Resource
    private AuthUserFeignService authUserFeignService;

    public AuthUser getUserInfo(String userName) {
        AuthUserDto authUserDTO = new AuthUserDto();
        authUserDTO.setUserName(userName);
        Result<AuthUserDto> result = authUserFeignService.getUserInfo(authUserDTO);
        AuthUser userInfo = new AuthUser();
        if (!result.getSuccess()) {
            return userInfo;
        }
        AuthUserDto data = result.getData();
        userInfo.setUserName(data.getUserName());
        userInfo.setNickName(data.getNickName());
        userInfo.setAvatar(data.getAvatar());
        return userInfo;
    }
}
