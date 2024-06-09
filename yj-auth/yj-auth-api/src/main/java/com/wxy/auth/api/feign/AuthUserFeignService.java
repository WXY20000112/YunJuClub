package com.wxy.auth.api.feign;

import com.wxy.auth.api.entity.AuthUserDto;
import com.wxy.auth.api.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUser Feign客户端
 * @author: 32115
 * @create: 2024-05-23 18:13
 */
@FeignClient("yj-auth")
public interface AuthUserFeignService {

    @RequestMapping("/user/getUserInfo")
    Result<AuthUserDto> getUserInfo(@RequestBody AuthUserDto authUserDto);

    @RequestMapping("/user/getUserInfoList")
    Result<List<AuthUserDto>> getUserInfoList(@RequestParam("userNameList") List<String> userNameList);
}
