package com.wxy.practice.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSubjectVO
 * @author: 32115
 * @create: 2024-06-06 11:13
 */
@Data
public class PracticeSubjectVO implements Serializable {

    /**
     * 题目名称
     */
    private String subjectName;

    /**
     * 题目类型
     */
    private Integer subjectType;

    /**
     * 答案列表
     */
    private List<String> answerContentList;

    /**
     * 单选、多选、判断题目答案
     */
    private List<PracticeSubjectOptionVO> optionList;
}
