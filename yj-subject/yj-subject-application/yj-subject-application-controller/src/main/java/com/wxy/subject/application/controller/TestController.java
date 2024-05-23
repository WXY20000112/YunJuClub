package com.wxy.subject.application.controller;

import com.wxy.subject.common.entity.Result;
import com.wxy.subject.infra.rpc.entity.AuthUser;
import com.wxy.subject.infra.rpc.feign.AuthUserRpc;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: 测试
 * @author: 32115
 * @create: 2024-05-23 18:22
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private AuthUserRpc authUserRpc;

    @RequestMapping("/testFeign")
    public Result<AuthUser> getUserInfo(@RequestParam("userName") String userName) {
        return Result.success(authUserRpc.getUserInfo(userName));
    }
}
