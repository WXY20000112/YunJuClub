package com.wxy.auth.application.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.wxy.auth.application.converter.AuthUserDtoConverter;
import com.wxy.auth.application.dto.AuthUserDto;
import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.common.entity.Result;
import com.wxy.auth.domain.entity.AuthUserBO;
import com.wxy.auth.domain.service.AuthUserDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserController
 * @author: 32115
 * @create: 2024-05-19 16:39
 */
@RestController
@RequestMapping("/user")
@Slf4j
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
    public Result<Boolean> register(@RequestBody AuthUserDto authUserDto) {
        try {
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
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 修改用户信息
     * @date: 2024/5/21
     * @param: authUserDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/update")
    @AopLogAnnotations
    public Result<Boolean> updateUserInfo(@RequestBody AuthUserDto authUserDto) {
        try {
            // 参数校验
            Preconditions.checkNotNull(authUserDto.getId(), "用户id不能为空");
            // Dto转BO
            AuthUserBO authUserBO = AuthUserDtoConverter
                    .CONVERTER.converterDtoToBo(authUserDto);
            // 调用业务层
            return authUserDomainService.updateUserInfo(authUserBO)
                    ? Result.success("修改成功") : Result.error("修改失败");
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 删除用户 逻辑删除
     * @date: 2024/5/21
     * @param: authUserDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/delete")
    @AopLogAnnotations
    public Result<Boolean> deleteAuthUser(@RequestBody AuthUserDto authUserDto){
        try {
            // 参数校验
            Preconditions.checkNotNull(authUserDto.getId(), "用户id不能为空");
            // Dto转BO
            AuthUserBO authUserBO = AuthUserDtoConverter
                    .CONVERTER.converterDtoToBo(authUserDto);
            // 调用业务层
            return authUserDomainService.deleteAuthUser(authUserBO)
                    ? Result.success("删除成功") : Result.error("删除失败");
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 启用/禁用用户
     * @date: 2024/5/21
     * @param: authUserDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/changeStatus")
    @AopLogAnnotations
    public Result<Boolean> changeAuthUserStatus(@RequestBody AuthUserDto authUserDto){
        try {
            // 参数校验
            Preconditions.checkNotNull(authUserDto.getId(), "用户id不能为空");
            // Dto转BO
            AuthUserBO authUserBO = AuthUserDtoConverter
                    .CONVERTER.converterDtoToBo(authUserDto);
            // 调用业务层
            return authUserDomainService.changeAuthUserStatus(authUserBO)
                    ? Result.success("修改成功") : Result.error("修改失败");
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 根据用户传递的验证码进行登录
     * @date: 2024/5/21
     * @param: code
     * @return: Result<SaTokenInfo>
     */
    @RequestMapping("/doLogin")
    @AopLogAnnotations
    public Result<SaTokenInfo> login(@RequestParam("validCode") String code) {
        try {
            // 参数校验
            Preconditions.checkNotNull(code, "验证码不能为空");
            // 调用业务层
            return authUserDomainService.login(code);
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 获取用户信息
     * @date: 2024/5/21
     * @param: authUserDto
     * @return: Result<AuthUserDto>
     */
    @RequestMapping("/getUserInfo")
    @AopLogAnnotations
    public Result<AuthUserDto> getUserInfo(@RequestBody AuthUserDto authUserDto) {
        try {
            // 参数校验
            Preconditions.checkNotNull(authUserDto.getUserName(), "用户名不能为空");
            // Dto转BO
            AuthUserBO authUserBO = AuthUserDtoConverter
                    .CONVERTER.converterDtoToBo(authUserDto);
            // 调用业务层
            return Result.success(AuthUserDtoConverter
                    .CONVERTER.converterBoToDto(authUserDomainService.getUserInfo(authUserBO)));
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 退出登录
     * @date: 2024/5/21
     * @return: Result<String>
     */
    @RequestMapping("/logOut")
    public Result<String> logOut(@RequestParam("userName") String userName) {
        try {
            // 参数校验
            Preconditions.checkNotNull(userName, "用户名不能为空");
            // 调用StpUtil.logout()方法 退出登录
            StpUtil.logout(userName);
            return Result.success("退出成功");
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }
}
