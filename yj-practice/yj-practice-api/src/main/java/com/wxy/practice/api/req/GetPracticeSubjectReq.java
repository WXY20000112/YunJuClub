package com.wxy.practice.api.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: GetPracticeSubjectReq
 * @author: 32115
 * @create: 2024-06-06 11:16
 */
@Data
public class GetPracticeSubjectReq implements Serializable {

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 题目类型
     */
    private Integer subjectType;
}
