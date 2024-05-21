package com.wxy.wechat.handler;

import com.wxy.wechat.enums.WeChatTypeEnum;

import java.util.Map;

/**
 * @program: YunJuClub
 * @description: 微信接收消息类型接口规范
 * @author: 32115
 * @create: 2024-04-03 19:45
 */
public interface WeChatTypeHandler {

    // 用于获取枚举类型
    WeChatTypeEnum getType();

    // 具体执行的业务 不同的策略类实现不同消息处理方式
    String dealMsg(Map<String, String> parseXmlMap);
}
