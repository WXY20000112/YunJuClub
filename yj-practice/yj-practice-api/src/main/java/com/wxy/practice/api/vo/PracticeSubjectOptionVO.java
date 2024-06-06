package com.wxy.practice.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSubjectOptionVO
 * @author: 32115
 * @create: 2024-06-06 11:14
 */
@Data
public class PracticeSubjectOptionVO implements Serializable {

    /**
     * 答案类型
     */
    private Integer optionType;

    /**
     * 答案内容
     */
    private String optionContent;

    /**
     * 是否正確
     */
    private Integer isCorrect;
}
