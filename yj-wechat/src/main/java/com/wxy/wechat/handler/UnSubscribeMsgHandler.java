package com.wxy.wechat.handler;

import com.wxy.wechat.enums.WeChatTypeEnum;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: YunJuClub
 * @description: 处理关注类消息的测策略类
 * @author: 32115
 * @create: 2024-04-03 19:49
 */

@Component
public class UnSubscribeMsgHandler implements WeChatTypeHandler {

    /**
     * @author: 32115
     * @description: 返回本类要处理的枚举类型
     * @date: 2024/4/3
     * @return: WeChatTypeEnum
     */
    @Override
    public WeChatTypeEnum getType() {
        return WeChatTypeEnum.UNSUBSCRIBE;
    }

    /**
     * @author: 32115
     * @description: 接收到关注事件是要进行的业务逻辑
     * @date: 2024/4/3
     * @return: String
     */
    @Override
    public String dealMsg(Map<String, String> parseXmlMap) {
        return null;
    }
}
