package com.wxy.subject.domain.handler;

import com.wxy.subject.common.enums.SubjectTypeEnum;
import com.wxy.subject.domain.entity.SubjectInfoBO;
import com.wxy.subject.infra.entity.SubjectRadio;
import com.wxy.subject.infra.service.SubjectRadioService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: 单选题处理器
 * @author: 32115
 * @create: 2024-05-15 17:43
 */
@Component
public class RadioHandler implements SubjectTypeHandler {

    @Resource
    private SubjectRadioService subjectRadioService;

    /**
     * @author: 32115
     * @description: 获取处理器类型
     * @date: 2024/5/15
     * @return: SubjectTypeEnum
     */
    @Override
    public SubjectTypeEnum getHandlerType() {
        return SubjectTypeEnum.RADIO;
    }

    /**
     * @author: 32115
     * @description: 添加单选题目
     * @date: 2024/5/15
     * @param: subjectInfoBO
     * @return: Boolean
     */
    @Override
    public Boolean addSubject(SubjectInfoBO subjectInfoBO) {
        // 校验答案内容是否为空
        if (CollectionUtils.isEmpty(subjectInfoBO.getOptionBoList())) return false;
        // 使用集合存储单选题选项信息 一个选项对应一个Radio对象
        List<SubjectRadio> subjectRadioList = subjectInfoBO.getOptionBoList().stream()
                .map(optionBo -> {
                    SubjectRadio subjectRadio = new SubjectRadio();
                    // 设置所属题目id
                    subjectRadio.setSubjectId(subjectInfoBO.getId());
                    // 设置选项类型
                    subjectRadio.setOptionType(optionBo.getOptionType());
                    // 设置选项内容
                    subjectRadio.setOptionContent(optionBo.getOptionContent());
                    // 设置选项是否正确
                    subjectRadio.setIsCorrect(optionBo.getIsCorrect());
                    return subjectRadio;
                }).toList();
        // 添加单选题选项信息并返回
        return subjectRadioService.addRadioSubject(subjectRadioList);
    }
}
