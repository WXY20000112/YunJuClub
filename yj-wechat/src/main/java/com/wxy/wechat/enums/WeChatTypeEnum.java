package com.wxy.wechat.enums;

import lombok.Getter;

/**
 * @program: YunJuClub
 * @description: 接受的消息类型枚举类
 * @author: 32115
 * @create: 2024-04-03 19:35
 */
@Getter
public enum WeChatTypeEnum {

    SUBSCRIBE("event.subscribe", "关注事件"),
    UNSUBSCRIBE("event.unsubscribe", "取消关注事件"),
    TEXT_MSG("text", "文本消息");

    private final String type;

    private final String msg;

    WeChatTypeEnum(String type, String msg){
        this.type = type;
        this.msg = msg;
    }

    public static WeChatTypeEnum getWeChatTypeEnums(String type){
        for (WeChatTypeEnum weChatTypeEnums : WeChatTypeEnum.values()){
            if (weChatTypeEnums.type.equals(type)){
                return weChatTypeEnums;
            }
        }
        return null;
    }
}
