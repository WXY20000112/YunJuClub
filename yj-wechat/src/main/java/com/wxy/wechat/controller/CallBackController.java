package com.wxy.wechat.controller;

import com.wxy.wechat.constant.WeChatConstant;
import com.wxy.wechat.handler.WeChatHandlerFactory;
import com.wxy.wechat.handler.WeChatTypeHandler;
import com.wxy.wechat.utils.MessageUtil;
import com.wxy.wechat.utils.SHA1;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: YunJuClub
 * @description: 微信接口回调controller
 * @author: 32115
 * @create: 2024-04-03 16:02
 */
@RestController
public class CallBackController {

    @Resource
    private WeChatHandlerFactory weChatHandlerFactory;

    @GetMapping("/callback")
    public String callBack(
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("echostr") String echostr){
        String sha1 = SHA1.getSHA1(WeChatConstant.token, timestamp, nonce, "");
        if (signature.equals(sha1)){
            return echostr;
        }
        return "Unknown";
    }

    @PostMapping(value = "/callback", produces = "application/xml;charset=UTF-8")
    public String callBack(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(value = "msg_signature", required = false) String msgSignature){
        System.out.println("requestBody : " + "\n" + requestBody);
        System.out.println("signature : "  + signature);
        System.out.println("timestamp : "  + timestamp);
        System.out.println("nonce : "  + nonce);
        System.out.println("msgSignature : "  + msgSignature);

        // 解析xml
        Map<String, String> parseXmlMap = MessageUtil.parseXml(requestBody);
        // 获取MsgType 微信返回的普通类型消息中只有MsgType字段
        String msgType = parseXmlMap.get("MsgType");
        // 获取Event
        // 微信返回的事件类型消息中 MsgType字段为event 还会多出一个Event字段
        // 所以该字段可能有可能没有 没有的话就不再拼接
        if (parseXmlMap.get("Event") != null){
            msgType = msgType + "." + parseXmlMap.get("Event");
        }
        // 将类型值进行拼接并获取对应handler
        WeChatTypeHandler handler = weChatHandlerFactory.getHandler(msgType);
        // 调用对应handler的处理消息的方法进行消息处理
        // 返回xml信息
        return handler.dealMsg(parseXmlMap);
    }
}
