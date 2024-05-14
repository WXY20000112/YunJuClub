package com.wxy.subject.common.enums;

import lombok.Getter;

/**
 * @program: YunJuClub-Flex
 * @description: 是否删除枚举
 * @author: 32115
 * @create: 2024-05-14 17:13
 */
@Getter
public enum IsDeletedEnum {

    DELETED(1, "已删除"),
    UN_DELETED(0, "未删除");

    // 状态码
    private final Integer code;

    // 状态信息
    private final String message;

    IsDeletedEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
