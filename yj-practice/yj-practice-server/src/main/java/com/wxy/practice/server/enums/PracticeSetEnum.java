package com.wxy.practice.server.enums;

import lombok.Getter;

/**
 * @program: YunJuClub
 * @description: 题目类型枚举类
 * @author: 32115
 * @create: 2024-03-21 08:47
 */

@Getter
public enum PracticeSetEnum {

    COMPLETE(1, "已完成"),
    UN_COMPLETE(0, "未完成");

    // 状态码
    private final int code;

    // 提示信息
    private final String msg;

    PracticeSetEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
