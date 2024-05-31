package com.wxy.subject.common.enums;

import lombok.Getter;

/**
 * @program: YunJuClub-Flex
 * @description: 题目点赞状态枚举
 * @author: 32115
 * @create: 2024-05-14 17:13
 */
@Getter
public enum SubjectLikedStatusEnum {

    LIKED(1, "已点赞"),
    UN_LIKED(0, "未点赞");

    // 状态码
    private final Integer code;

    // 状态信息
    private final String message;

    SubjectLikedStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
