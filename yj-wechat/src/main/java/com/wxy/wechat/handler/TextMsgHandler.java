package com.wxy.wechat.handler;

import com.wxy.wechat.constant.WeChatConstant;
import com.wxy.wechat.enums.WeChatKeyWordEnum;
import com.wxy.wechat.enums.WeChatTypeEnum;
import com.wxy.wechat.utils.RedisUtil;
import com.wxy.wechat.utils.ValidateCodeUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: YunJuClub
 * @description: 处理文本消息的策略类
 * @author: 32115
 * @create: 2024-04-03 19:52
 */

@Component
public class TextMsgHandler implements WeChatTypeHandler {

    @Resource
    private RedisUtil redisUtil;

    /**
     * @author: 32115
     * @description: 返回本类要处理的枚举类型
     * @date: 2024/4/3
     * @return: WeChatTypeEnum
     */
    @Override
    public WeChatTypeEnum getType() {
        return WeChatTypeEnum.TEXT_MSG;
    }

    /**
     * @author: 32115
     * @description: 处理文本消息的业务逻辑
     * @date: 2024/4/3
     * @return: String
     */
    @Override
    public String dealMsg(Map<String, String> parseXmlMap) {
        // 如果回复的不是验证码这个关键词就不做自定回复
        if (!parseXmlMap.get("Content").equals(WeChatKeyWordEnum.CAPTCHA.getWord())){
            return null;
        }
        // 获取fromUserName和toUserName
        String fromUserName = parseXmlMap.get("FromUserName");
        String toUserName = parseXmlMap.get("ToUserName");
        // 使用工具类生成自定义验证码
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
        String returnCode =
                "您正在登陆云桔社区，验证码为：" + code + "，该验证码5分钟内有效，请勿泄露于他人。";
        // 将用户的验证码存放到redis中
        // 创建key
        String codeKey = redisUtil.buildKey(WeChatConstant.WECHAT_CODE_PREFIX, code);
        // 将code放入缓存 设置过期时间5分钟
        redisUtil.setNx(codeKey, fromUserName, 5L, TimeUnit.MINUTES);
        return "<xml>\n" +
                "  <ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + toUserName + "]]></FromUserName>\n" +
                "  <CreateTime>12345678</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[" + returnCode + "]]></Content>\n" +
                "</xml>";
    }
}
