package com.wxy.practice.server.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSubjectDTO
 * @author: 32115
 * @create: 2024-06-04 12:04
 */
@Data
public class PracticeSubjectDTO implements Serializable {

    /**
     * 分类与标签组合的ids
     */
    private List<String> assembleIds;

    /**
     * 题目类型
     */
    private Integer subjectType;

    /**
     * 题目数量
     */
    private Integer subjectCount;

    /**
     * 要排除的题目id
     */
    private List<Long> excludeSubjectIds;

    /**
     * 题目id
     */
    private Long subjectId;
}
