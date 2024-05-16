package com.wxy.subject.domain.handler;

import com.wxy.subject.common.enums.SubjectTypeEnum;
import com.wxy.subject.domain.entity.SubjectFactoryBO;
import com.wxy.subject.domain.entity.SubjectInfoBO;
import com.wxy.subject.domain.entity.SubjectOptionBO;
import com.wxy.subject.infra.entity.SubjectMultiple;
import com.wxy.subject.infra.service.SubjectMultipleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: 多选题处理器
 * @author: 32115
 * @create: 2024-05-15 17:35
 */
@Component
public class MultipleHandler implements SubjectTypeHandler {

    @Resource
    private SubjectMultipleService subjectMultipleService;

    /**
     * @author: 32115
     * @description: 获取处理器类型
     * @date: 2024/5/15
     * @return: SubjectTypeEnum
     */
    @Override
    public SubjectTypeEnum getHandlerType() {
        return SubjectTypeEnum.MULTIPLE;
    }

    /**
     * @author: 32115
     * @description: 根据题目id获取多选题目信息
     * @date: 2024/5/16
     * @param: id
     * @return: SubjectFactoryBO
     */
    @Override
    public SubjectFactoryBO getBySubjectId(Long id) {
        // 根据subjectId查询信息
        List<SubjectMultiple> subjectMultipleList =
                subjectMultipleService.getBySubjectId(id);
        SubjectFactoryBO subjectFactoryBO = new SubjectFactoryBO();
        // 将multiple转为optionBo
        List<SubjectOptionBO> optionBOList = subjectMultipleList.stream().map(subjectMultiple -> {
            SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
            subjectOptionBO.setOptionType(subjectMultiple.getOptionType());
            subjectOptionBO.setOptionContent(subjectMultiple.getOptionContent());
            subjectOptionBO.setIsCorrect(subjectMultiple.getIsCorrect());
            return subjectOptionBO;
        }).toList();
        subjectFactoryBO.setOptionList(optionBOList);
        return subjectFactoryBO;
    }

    /**
     * @author: 32115
     * @description: 添加多选题目
     * @date: 2024/5/15
     * @param: subjectInfoBO
     * @return: Boolean
     */
    @Override
    public Boolean addSubject(SubjectInfoBO subjectInfoBO) {
        // 校验答案内容是否为空
        if (CollectionUtils.isEmpty(subjectInfoBO.getOptionList())) return false;
        // 使用集合存储多选题选项信息 一个选项对应一个Multiple对象
        List<SubjectMultiple> subjectMultipleList = subjectInfoBO.getOptionList().stream()
                .map(subjectOptionBO -> {
                    SubjectMultiple subjectMultiple = new SubjectMultiple();
                    // 设置选项内容
                    subjectMultiple.setOptionContent(subjectOptionBO.getOptionContent());
                    // 设置选项类型
                    subjectMultiple.setOptionType(subjectOptionBO.getOptionType());
                    // 设置该选项是否正确
                    subjectMultiple.setIsCorrect(subjectOptionBO.getIsCorrect());
                    // 设置所属题目id
                    subjectMultiple.setSubjectId(subjectInfoBO.getId());
                    return subjectMultiple;
                }).toList();
        // 添加多选题选项信息并返回
        return subjectMultipleService.addMultipleSubject(subjectMultipleList);
    }
}
