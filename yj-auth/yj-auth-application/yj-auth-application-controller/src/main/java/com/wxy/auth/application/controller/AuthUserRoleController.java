package com.wxy.auth.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.auth.api.entity.Result;
import com.wxy.auth.application.converter.AuthUserRoleDtoConverter;
import com.wxy.auth.application.dto.AuthUserRoleDto;
import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.domain.entity.AuthUserRoleBO;
import com.wxy.auth.domain.service.AuthUserRoleDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRoleController
 * @author: 32115
 * @create: 2024-05-21 14:45
 */
@RestController
@RequestMapping("/userRole")
@Slf4j
public class AuthUserRoleController {

    @Resource
    private AuthUserRoleDomainService authUserRoleDomainService;

    /**
     * @author: 32115
     * @description: 添加用户与角色关联关系
     * @date: 2024/5/21
     * @param: authUserRoleDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/add")
    @AopLogAnnotations
    public Result<Boolean> addAuthUserRole(@RequestBody AuthUserRoleDto authUserRoleDto) {
        try {
            // 参数校验
            Preconditions.checkNotNull(authUserRoleDto.getUserId(), "用户id不能为空");
            Preconditions.checkNotNull(authUserRoleDto.getRoleIdList(), "角色id列表不能为空");
            // Dto转Bo
            AuthUserRoleBO authUserRoleBO = AuthUserRoleDtoConverter
                    .CONVERTER.converterDtoToBo(authUserRoleDto);
            // 调用业务层
            return authUserRoleDomainService.addAuthUserRole(authUserRoleBO) ?
                    Result.success() : Result.error();
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }
}
