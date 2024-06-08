package com.wxy.practice.server.enums;

import lombok.Getter;

/**
 * @program: YunJuClub
 * @description: 题目类型枚举类
 * @author: 32115
 * @create: 2024-03-21 08:47
 */

@Getter
public enum AnswerStatusEnum {

    ERROR(0, "错误"),
    CORRECT(1, "正确");

    // 状态码
    private final int code;

    // 提示信息
    private final String msg;

    AnswerStatusEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
