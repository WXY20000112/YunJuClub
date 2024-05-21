package com.wxy.auth.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.auth.application.converter.AuthRolePermissionDtoConverter;
import com.wxy.auth.application.dto.AuthRolePermissionDto;
import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.common.entity.Result;
import com.wxy.auth.domain.entity.AuthRolePermissionBO;
import com.wxy.auth.domain.service.AuthRolePermissionDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRolePermissionController
 * @author: 32115
 * @create: 2024-05-19 15:14
 */
@RestController
@RequestMapping("/rolePermission")
@Slf4j
public class AuthRolePermissionController {

    @Resource
    private AuthRolePermissionDomainService authRolePermissionDomainService;

    /**
     * @author: 32115
     * @description: 添加角色与权限的关联关系
     * @date: 2024/5/19
     * @param: authRolePermissionDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/add")
    @AopLogAnnotations
    public Result<Boolean> addAuthRolePermission(@RequestBody AuthRolePermissionDto authRolePermissionDto){
        try {
            // 参数校验
            Preconditions.checkNotNull(authRolePermissionDto.getRoleId(), "角色id不能为空");
            Preconditions.checkNotNull(authRolePermissionDto.getPermissionIdList(), "权限id列表不能为空");
            // Dto转BO
            AuthRolePermissionBO authRolePermissionBO = AuthRolePermissionDtoConverter
                    .CONVERTER.converterDtoToBo(authRolePermissionDto);
            // 调用业务层
            return authRolePermissionDomainService.addAuthRolePermission(authRolePermissionBO)
                    ? Result.success(true) : Result.error(false);
        } catch (Exception e) {
            // 打印日志
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }
}
