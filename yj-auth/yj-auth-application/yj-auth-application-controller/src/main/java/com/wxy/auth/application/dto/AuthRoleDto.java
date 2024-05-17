package com.wxy.auth.application.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: AuthRoleBO实体类
 * @author: 32115
 * @create: 2024-05-17 14:17
 */
@Data
public class AuthRoleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -48884489779964726L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态 0为正常 1为禁用
     */
    private Integer roleStatus;

    /**
     * 角色唯一标识
     */
    private String roleKey;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
