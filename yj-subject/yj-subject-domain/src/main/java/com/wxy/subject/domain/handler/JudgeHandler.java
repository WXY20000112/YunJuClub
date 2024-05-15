package com.wxy.subject.domain.handler;

import com.wxy.subject.common.enums.SubjectTypeEnum;
import com.wxy.subject.domain.entity.SubjectInfoBO;
import com.wxy.subject.infra.entity.SubjectJudge;
import com.wxy.subject.infra.service.SubjectJudgeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @program: YunJuClub-Flex
 * @description: 判断题handler处理器
 * @author: 32115
 * @create: 2024-05-15 17:20
 */
@Component
public class JudgeHandler implements SubjectTypeHandler {

    @Resource
    private SubjectJudgeService subjectJudgeService;

    /**
     * @author: 32115
     * @description: 告诉调用者该处理器处理什么类型的题目
     * @date: 2024/5/15
     * @return: SubjectTypeEnum
     */
    @Override
    public SubjectTypeEnum getHandlerType() {
        return SubjectTypeEnum.JUDGE;
    }

    /**
     * @author: 32115
     * @description: 添加判断题目
     * @date: 2024/5/15
     * @param: subjectInfoBO
     * @return: Boolean
     */
    @Override
    public Boolean addSubject(SubjectInfoBO subjectInfoBO) {
        // 校验答案内容是否为空
        if (CollectionUtils.isEmpty(subjectInfoBO.getOptionBoList())) return false;
        // 添加判断题
        SubjectJudge subjectJudge = new SubjectJudge();
        // 设置所属题目id
        subjectJudge.setSubjectId(subjectInfoBO.getId());
        // 设置判断题答案内容
        subjectJudge.setIsCorrect(subjectInfoBO.getOptionBoList().getFirst().getIsCorrect());
        // 添加返回
        return subjectJudgeService.addJudgeSubject(subjectJudge);
    }
}
