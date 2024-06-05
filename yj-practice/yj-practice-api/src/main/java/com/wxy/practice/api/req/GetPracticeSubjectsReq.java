package com.wxy.practice.api.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: GetPracticeSubjectsReq
 * @author: 32115
 * @create: 2024-06-05 14:38
 */
@Data
public class GetPracticeSubjectsReq implements Serializable {

    /**
     * 套题id
     */
    private Long setId;

    /**
     * 练习id
     */
    private Long practiceId;
}
