package com.wxy.subject.api.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategoryBO实体类
 * @author: 32115
 * @create: 2024-05-13 11:31
 */
@Data
public class SubjectInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 649887552684579511L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 题目名称
     */
    private String subjectName;

    /**
     * 题目难易度
     */
    private Integer subjectDifficult;

    /**
     * 出题人
     */
    private String settleName;

    /**
     * 题目类型 1单选 2多选 3判断 4简答
     */
    private Integer subjectType;

    /**
     * 题目分数
     */
    private Integer subjectScore;

    /**
     * 题目解析
     */
    private String subjectParse;

    /**
     * 是否删除 0为未删除 1为已删除
     */
    private Integer isDeleted;

    /**
     * 题目数量
     */
    private Integer subjectCount;

    /**
     * 要排除题目的id列表
     */
    private List<Long> excludeSubjectIds;

    /**
     * 分类与标签组合的ids
     */
    private List<String> assembleIds;

    /**
     * 题目答案选项列表
     */
    private List<SubjectOptionDto> optionList;

}

