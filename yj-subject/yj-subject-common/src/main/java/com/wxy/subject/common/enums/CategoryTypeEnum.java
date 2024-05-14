package com.wxy.subject.common.enums;

import lombok.Getter;

/**
 * @program: YunJuClub-Flex
 * @description: 分类类型枚举
 * @author: 32115
 * @create: 2024-05-14 17:13
 */
@Getter
public enum CategoryTypeEnum {

    FIRST_CATEGORY(1, "一级分类"),
    SECOND_CATEGORY(2, "二级分类"),
    THIRD_CATEGORY(2, "三级分类");

    // 状态码
    private final Integer code;

    // 状态信息
    private final String message;

    CategoryTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
