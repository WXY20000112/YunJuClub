package com.wxy.auth.api.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserBO实体类
 * @author: 32115
 * @create: 2024-05-17 14:19
 */
@Data
public class AuthUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 788808330473046974L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 账号名称
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态 0为正常 1为禁用
     */
    private Integer status;

    /**
     * 自我简介
     */
    private String introduce;

    /**
     * 扩展内容
     */
    private String extJson;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;
}
