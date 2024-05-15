package com.wxy.subject.domain.handler;

import com.wxy.subject.common.enums.SubjectTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: YunJuClub-Flex
 * @description: 用于获取对应的类型的处理器
 * @author: 32115
 * @create: 2024-05-15 17:06
 */
@Component
public class SubjectTypeFactory implements InitializingBean {

    @Resource
    private List<SubjectTypeHandler> subjectTypeHandlerList;

    // 用于存储所有的typeHandler类型
    private final Map<SubjectTypeEnum, SubjectTypeHandler> handlerMap = new HashMap<>();

    /**
     * @author: 32115
     * @description: 根据传入的type在map中拿到对应的handler
     * @date: 2024/5/15
     * @param: subjectType
     * @return: SubjectTypeHandler
     */
    public SubjectTypeHandler getHandler(int subjectType) {
        // 通过枚举类里面的geyByCode方法获取对应的枚举类型
        SubjectTypeEnum subjectTypeCode = SubjectTypeEnum.getByCode(subjectType);
        // 在map中拿到对应的枚举类型handler
        return handlerMap.get(subjectTypeCode);
    }

    /**
     * @author: 32115
     * @description: 类初始化前置处理器 类进行初始化的时候执行该方法 将handler放入map中
     * @date: 2024/5/15
     * @return: void
     */
    @Override
    public void afterPropertiesSet() {
        for (SubjectTypeHandler subjectTypeHandler : subjectTypeHandlerList) {
            // 将subjectTypeCode作为key 将handler放入map中
            handlerMap.put(subjectTypeHandler.getHandlerType(), subjectTypeHandler);
        }
    }
}
