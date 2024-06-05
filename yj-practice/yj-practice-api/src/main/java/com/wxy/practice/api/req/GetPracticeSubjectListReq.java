package com.wxy.practice.api.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: GetPracticeSubjectListReq
 * @author: 32115
 * @create: 2024-06-04 11:54
 */
@Data
public class GetPracticeSubjectListReq implements Serializable {

    /**
     * 分类与标签组合的ids
     */
    private List<String> assembleIds;
}
