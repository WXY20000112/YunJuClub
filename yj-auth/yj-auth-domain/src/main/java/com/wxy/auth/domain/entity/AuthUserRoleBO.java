package com.wxy.auth.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRoleBO实体类
 * @author: 32115
 * @create: 2024-05-17 14:22
 */
@Data
public class AuthUserRoleBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 646624418329448794L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;

    /**
     * 给用户添加的角色id列表
     */
    private List<Long> roleIdList;
}
