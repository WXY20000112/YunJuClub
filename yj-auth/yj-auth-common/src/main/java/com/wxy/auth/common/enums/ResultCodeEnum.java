package com.wxy.auth.common.enums;

import lombok.Getter;

/**
 * @program: YunJuClub-Flex
 * @description: 状态码枚举
 * @author: 32115
 * @create: 2024-05-14 17:13
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    UNAUTHORIZED(401, "未认证"),
    NOT_FOUND(404, "未找到");

    // 状态码
    private final Integer code;

    // 状态信息
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
