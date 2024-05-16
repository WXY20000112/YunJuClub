package com.wxy.subject.domain.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub
 * @description: 用于封装handler返回的结果
 * @author: 32115
 * @create: 2024-03-21 17:54
 */
@Data
public class SubjectFactoryBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -24223855485927853L;

    /**
     * 题目答案
     */
    private String subjectAnswer;

    /**
     * 题目答案选项列表
     */
    private List<SubjectOptionBO> optionList;

}
