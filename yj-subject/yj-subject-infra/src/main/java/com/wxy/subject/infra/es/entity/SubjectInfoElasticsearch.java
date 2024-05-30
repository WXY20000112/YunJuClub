package com.wxy.subject.infra.es.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoElasticsearch
 * @author: 32115
 * @create: 2024-05-28 12:38
 */
@Data
public class SubjectInfoElasticsearch implements Serializable {

    private Long subjectId;

    private Long docId;

    private String subjectName;

    private String subjectAnswer;

    private String createUser;

    private Long createTime;

    private Integer subjectType;

    private String keyWord;

    private BigDecimal score;

    private Integer pageNo;

    private Integer pageSize;

}
