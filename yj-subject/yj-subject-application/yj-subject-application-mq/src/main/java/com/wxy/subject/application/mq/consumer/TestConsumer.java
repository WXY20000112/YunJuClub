package com.wxy.subject.application.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @program: YunJuClub-Flex
 * @description: 消费者
 * @author: 32115
 * @create: 2024-06-09 11:25
 */
@Component
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "test-consumer-group")
@Slf4j
public class TestConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("接收到消息：{}", s);
    }
}
