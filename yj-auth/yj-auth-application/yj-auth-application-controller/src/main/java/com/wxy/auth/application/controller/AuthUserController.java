package com.wxy.auth.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.auth.application.converter.AuthUserDtoConverter;
import com.wxy.auth.application.dto.AuthUserDto;
import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.common.entity.Result;
import com.wxy.auth.domain.entity.AuthUserBO;
import com.wxy.auth.domain.service.AuthUserDomainService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserController
 * @author: 32115
 * @create: 2024-05-19 16:39
 */
@RestController
@RequestMapping("/user")
public class AuthUserController {

    @Resource
    private AuthUserDomainService authUserDomainService;

    /**
     * @author: 32115
     * @description: 注册新增用户
     * @date: 2024/5/19
     * @param: authUserDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/register")
    @AopLogAnnotations
    public Result<Boolean> register(@RequestBody AuthUserDto authUserDto){
        // 参数校验
        Preconditions.checkNotNull(authUserDto.getUserName(), "用户名不能为空");
        Preconditions.checkNotNull(authUserDto.getEmail(), "手机号不能为空");
        Preconditions.checkNotNull(authUserDto.getPhone(), "手机号不能为空");
        Preconditions.checkNotNull(authUserDto.getPassword(), "密码不能为空");
        Preconditions.checkNotNull(authUserDto.getSex(), "性别不能为空");
        // Dto转BO
        AuthUserBO authUserBO = AuthUserDtoConverter
                .CONVERTER.converterDtoToBo(authUserDto);
        // 调用业务层
        return authUserDomainService.register(authUserBO)
                ? Result.success() : Result.error();
    }
}
