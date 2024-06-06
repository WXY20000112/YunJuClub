package com.wxy.practice.server.enums;

import lombok.Getter;

/**
 * @program: YunJuClub
 * @description: 题目类型枚举类
 * @author: 32115
 * @create: 2024-03-21 08:47
 */

@Getter
public enum OrderTypeEnum {

    ORDER_DEFAULT(0, "默认排序"),
    ORDER_CREATE_TIME(1, "最新"),
    ORDER_HEAT(2, "最热");

    // 状态码
    private final int code;

    // 提示信息
    private final String msg;

    OrderTypeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
