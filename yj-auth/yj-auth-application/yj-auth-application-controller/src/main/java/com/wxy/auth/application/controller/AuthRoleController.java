package com.wxy.auth.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.auth.application.converter.AuthRoleDtoConverter;
import com.wxy.auth.application.dto.AuthRoleDto;
import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.common.entity.Result;
import com.wxy.auth.domain.entity.AuthRoleBO;
import com.wxy.auth.domain.service.AuthRoleDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRole角色控制器
 * @author: 32115
 * @create: 2024-05-18 14:43
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class AuthRoleController {

    @Resource
    private AuthRoleDomainService authRoleDomainService;

    /**
     * @author: 32115
     * @description: 添加角色
     * @date: 2024/5/18
     * @param: authRoleDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/add")
    @AopLogAnnotations
    public Result<Boolean> addRole(@RequestBody AuthRoleDto authRoleDto) {
        // 参数校验
        Preconditions.checkNotNull(authRoleDto.getRoleName(), "角色名称不能为空");
        Preconditions.checkNotNull(authRoleDto.getRoleKey(), "角色标识不能为空");
        // Dto转换
        AuthRoleBO authRoleBO = AuthRoleDtoConverter
                .CONVERTER.converterDtoToBo(authRoleDto);
        // 调用领域服务
        return authRoleDomainService.addRole(authRoleBO) ?
                Result.success() : Result.error();
    }

    /**
     * @author: 32115
     * @description: 修改角色
     * @date: 2024/5/19
     * @param: authRoleDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/update")
    @AopLogAnnotations
    public Result<Boolean> updateAuthRole(@RequestBody AuthRoleDto authRoleDto){
        // 参数校验
        Preconditions.checkNotNull(authRoleDto.getId(), "角色id不能为空");
        // Dto转换
        AuthRoleBO authRoleBO = AuthRoleDtoConverter
                .CONVERTER.converterDtoToBo(authRoleDto);
        // 调用领域服务
        return authRoleDomainService.updateAuthRole(authRoleBO) ?
                Result.success() : Result.error();
    }

    /**
     * @author: 32115
     * @description: 删除角色 逻辑删除
     * @date: 2024/5/19
     * @param: authRoleDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/delete")
    @AopLogAnnotations
    public Result<Boolean> deleteAuthRole(@RequestBody AuthRoleDto authRoleDto) {
        // 参数校验
        Preconditions.checkNotNull(authRoleDto.getId(), "角色id不能为空");
        // Dto转换
        AuthRoleBO authRoleBO = AuthRoleDtoConverter
                .CONVERTER.converterDtoToBo(authRoleDto);
        // 调用领域服务
        return authRoleDomainService.deleteAuthRole(authRoleBO) ?
                Result.success() : Result.error();
    }

    /**
     * @author: 32115
     * @description: 启用/禁用角色
     * @date: 2024/5/19
     * @param: authRoleDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/changeRoleStatus")
    @AopLogAnnotations
    public Result<Boolean> changeRoleStatus(@RequestBody AuthRoleDto authRoleDto) {
        // 参数校验
        Preconditions.checkNotNull(authRoleDto.getId(), "角色id不能为空");
        // Dto转换
        AuthRoleBO authRoleBO = AuthRoleDtoConverter
                .CONVERTER.converterDtoToBo(authRoleDto);
        // 调用领域服务
        return authRoleDomainService.changeRoleStatus(authRoleBO) ?
                Result.success() : Result.error();
   }
}
