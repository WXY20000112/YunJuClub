package com.wxy.wechat.handler;

import com.wxy.wechat.enums.WeChatTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: YunJuClub
 * @description: 接受微信消息类型工厂类 用于初始化所有策略类并获对应的策略类
 * @author: 32115
 * @create: 2024-04-03 19:59
 */
@Component
public class WeChatHandlerFactory implements InitializingBean {

    // 存储实现了WeChatTypeHandler接口的所有实现类
    @Resource
    private List<WeChatTypeHandler> weChatTypeHandlerList;

    // 定义一个hashmap存储枚举类型与策略接口的对应关系
    private final Map<WeChatTypeEnum, WeChatTypeHandler> handlerMap = new HashMap<>();

    /**
     * @author: 32115
     * @description: 定义获取策略类的方法 用于获取map中对应的策略类
     * @date: 2024/4/3
     * @param: type
     * @return: WeChatTypeHandler
     */
    public WeChatTypeHandler getHandler(String type){
        WeChatTypeEnum weChatTypeEnum = WeChatTypeEnum.getWeChatTypeEnums(type);
        return handlerMap.get(weChatTypeEnum);
    }
    /**
     * @author: 32115
     * @description: 后置处理器 加载该类后最后会执行该方法将策略类存储到map中
     * @date: 2024/4/3
     * @return: void
     */
    @Override
    public void afterPropertiesSet() {
        for (WeChatTypeHandler weChatTypeHandler : weChatTypeHandlerList) {
            handlerMap.put(weChatTypeHandler.getType(), weChatTypeHandler);
        }
    }
}
