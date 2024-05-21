package com.wxy.wechat.enums;

import lombok.Getter;

/**
 * @program: YunJuClub
 * @description: 接受的消息类型枚举类
 * @author: 32115
 * @create: 2024-04-03 19:35
 */
@Getter
public enum WeChatKeyWordEnum {

    CAPTCHA("验证码", "验证码-关键词");

    private final String word;

    private final String msg;

    WeChatKeyWordEnum(String word, String msg){
        this.word = word;
        this.msg = msg;
    }
}
