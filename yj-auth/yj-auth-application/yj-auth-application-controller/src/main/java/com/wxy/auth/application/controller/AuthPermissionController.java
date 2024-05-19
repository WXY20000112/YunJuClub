package com.wxy.auth.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.auth.application.converter.AuthPermissionDtoConverter;
import com.wxy.auth.application.dto.AuthPermissionDto;
import com.wxy.auth.common.aop.AopLogAnnotations;
import com.wxy.auth.common.entity.Result;
import com.wxy.auth.domain.entity.AuthPermissionBO;
import com.wxy.auth.domain.service.AuthPermissionDomainService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: 权限操作控制器
 * @author: 32115
 * @create: 2024-05-17 15:05
 */
@RestController
@RequestMapping("/permission")
public class AuthPermissionController {

    @Resource
    private AuthPermissionDomainService authPermissionDomainService;

    /**
     * @author: 32115
     * @description: 添加权限信息
     * @date: 2024/5/17
     * @param: authPermissionDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/add")
    @AopLogAnnotations
    public Result<Boolean> addPermission(@RequestBody AuthPermissionDto authPermissionDto){
        // 参数校验
        Preconditions.checkNotNull(authPermissionDto.getName(), "权限名称不能为空");
        Preconditions.checkNotNull(authPermissionDto.getType(), "权限类型不能为空");
        Preconditions.checkNotNull(authPermissionDto.getPermissionKey(), "权限标识不能为空");
        Preconditions.checkNotNull(authPermissionDto.getMenuUrl(), "权限菜单路由不能为空");
        Preconditions.checkNotNull(authPermissionDto.getParentId(), "父权限id不能为空");
        // Dto转BO
        AuthPermissionBO authPermissionBO = AuthPermissionDtoConverter
                .CONVERTER.converterDtoToBO(authPermissionDto);
        // 添加权限信息
        return authPermissionDomainService.addPermission(authPermissionBO) ?
                Result.success() : Result.error("添加成功");
    }

    /**
     * @author: 32115
     * @description: 更新权限信息
     * @date: 2024/5/17
     * @param: authPermissionDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/update")
    @AopLogAnnotations
    public Result<Boolean> updateAuthPermission(@RequestBody AuthPermissionDto authPermissionDto){
        // 参数校验
        Preconditions.checkNotNull(authPermissionDto.getId(), "权限id不能为空");
        // Dto转BO
        AuthPermissionBO authPermissionBO = AuthPermissionDtoConverter
                .CONVERTER.converterDtoToBO(authPermissionDto);
        // 更新权限信息
        return authPermissionDomainService.updateAuthPermission(authPermissionBO) ?
                Result.success() : Result.error("修改失败");
    }

    /**
     * @author: 32115
     * @description: 删除权限信息 逻辑删除
     * @date: 2024/5/17
     * @param: authPermissionDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/delete")
    @AopLogAnnotations
    public Result<Boolean> deletePermission(@RequestBody AuthPermissionDto authPermissionDto){
        // 参数校验
        Preconditions.checkNotNull(authPermissionDto.getId(), "权限id不能为空");
        // Dto转BO
        AuthPermissionBO authPermissionBO = AuthPermissionDtoConverter
                .CONVERTER.converterDtoToBO(authPermissionDto);
        // 删除权限信息
        return authPermissionDomainService.deleteAuthPermission(authPermissionBO) ?
                Result.success(true) : Result.error("删除失败");
    }

    /**
     * @author: 32115
     * @description: 更新权限状态
     * @date: 2024/5/17
     * @param: authPermissionDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/changePermissionStatus")
    @AopLogAnnotations
    public Result<Boolean> changePermissionStatus(@RequestBody AuthPermissionDto authPermissionDto){
        // 参数校验
        Preconditions.checkNotNull(authPermissionDto.getId(), "权限id不能为空");
        // Dto转BO
        AuthPermissionBO authPermissionBO = AuthPermissionDtoConverter
                .CONVERTER.converterDtoToBO(authPermissionDto);
        // 更新权限状态
        return authPermissionDomainService.changePermissionStatus(authPermissionBO) ?
                Result.success(true) : Result.error("修改失败");
    }

    /**
     * @author: 32115
     * @description: 更新权限菜单显示状态
     * @date: 2024/5/17
     * @param: authPermissionDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/changePermissionMenuShow")
    @AopLogAnnotations
    public Result<Boolean> changeMenuShowStatus(@RequestBody AuthPermissionDto authPermissionDto){
        // 参数校验
        Preconditions.checkNotNull(authPermissionDto.getId(), "权限id不能为空");
        // Dto转BO
        AuthPermissionBO authPermissionBO = AuthPermissionDtoConverter
                .CONVERTER.converterDtoToBO(authPermissionDto);
        // 更新权限状态
        return authPermissionDomainService.changePermissionMenuShowStatus(authPermissionBO) ?
                Result.success(true) : Result.error("修改失败");
    }

    /**
     * @author: 32115
     * @description: 根据用户名获取权限列表
     * @date: 2024/5/17
     * @param: userName
     * @return: Result<List < String>>
     */
    @RequestMapping("/getPermission")
    @AopLogAnnotations
    public Result<List<String>> getPermissionList(@RequestParam(name = "userName") String userName){
        // 参数校验
        Preconditions.checkNotNull(userName, "用户名不能为空");
        // 获取权限列表
        return Result.success(authPermissionDomainService.getPermissionList(userName));
    }
}
