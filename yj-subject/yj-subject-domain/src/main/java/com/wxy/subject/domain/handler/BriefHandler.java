package com.wxy.subject.domain.handler;

import com.wxy.subject.common.constant.SubjectConstant;
import com.wxy.subject.common.enums.SubjectTypeEnum;
import com.wxy.subject.domain.entity.SubjectInfoBO;
import com.wxy.subject.infra.entity.SubjectBrief;
import com.wxy.subject.infra.service.SubjectBriefService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @program: YunJuClub-Flex
 * @description: 简答题对应的处理器
 * @author: 32115
 * @create: 2024-05-15 17:12
 */
@Component
public class BriefHandler implements SubjectTypeHandler {

    @Resource
    private SubjectBriefService subjectBriefService;

    /**
     * @author: 32115
     * @description: 告诉调用者我这个类是什么类型的处理器
     * @date: 2024/5/15
     * @return: SubjectTypeEnum
     */
    @Override
    public SubjectTypeEnum getHandlerType() {
        return SubjectTypeEnum.BRIEF;
    }

    /**
     * @author: 32115
     * @description: 新增简答题
     * @date: 2024/5/15
     * @param: subjectInfoBO
     * @return: Boolean
     */
    @Override
    public Boolean addSubject(SubjectInfoBO subjectInfoBO) {
        // 校验答案内容是否为空 若为空设置一个初始化的答案
        if (subjectInfoBO.getSubjectAnswer() == null){
            subjectInfoBO.setSubjectAnswer(SubjectConstant.SUBJECT_ANSWER_INIT);
        }
        // 获取简答题对应的题目信息
        SubjectBrief subjectBrief = new SubjectBrief();
        // 设置题目答案
        subjectBrief.setSubjectAnswer(subjectInfoBO.getSubjectAnswer());
        // 设置所属题目id
        subjectBrief.setSubjectId(subjectInfoBO.getId());
        return subjectBriefService.addBriefSubject(subjectBrief);
    }
}
