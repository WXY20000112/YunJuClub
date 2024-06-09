package com.wxy.subject.application.mq.producer;

import com.wxy.subject.common.entity.Result;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: 生产者
 * @author: 32115
 * @create: 2024-06-09 11:20
 */
@RequestMapping("/subject/producer")
@RestController
public class TestProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("/test")
    public Result<Boolean> test(@RequestParam("msg") String msg) {
        rocketMQTemplate.convertAndSend("test-topic", "RockerMQ发送测试:" + msg);
        return Result.success();
    }
}
