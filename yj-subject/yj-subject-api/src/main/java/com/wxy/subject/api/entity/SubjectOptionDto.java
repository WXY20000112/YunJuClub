package com.wxy.subject.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YunJuClub
 * @description: 题目答案选项列表
 * @author: 32115
 * @create: 2024-03-20 09:23
 */
@Data
public class SubjectOptionDto implements Serializable {

    /**
     * 答案选项标识
     */
    private Integer optionType;

    /**
     * 题目答案选项内容
     */
    private String optionContent;

    /**
     * 题目答案选项是否正确
     */
    private Integer isCorrect;
}
