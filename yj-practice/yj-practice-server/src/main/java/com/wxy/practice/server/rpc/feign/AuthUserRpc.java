package com.wxy.practice.server.rpc.feign;

import com.wxy.auth.api.entity.AuthUserDto;
import com.wxy.auth.api.entity.Result;
import com.wxy.auth.api.feign.AuthUserFeignService;
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

    public AuthUserDto getUserInfo(String userName) {
        AuthUserDto authUserDTO = new AuthUserDto();
        authUserDTO.setUserName(userName);
        Result<AuthUserDto> result =
                authUserFeignService.getUserInfo(authUserDTO);
        if (!result.getSuccess()) return new AuthUserDto();
        return result.getData();
    }
}
