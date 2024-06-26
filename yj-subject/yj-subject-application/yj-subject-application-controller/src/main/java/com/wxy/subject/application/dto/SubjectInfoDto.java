package com.wxy.subject.application.dto;

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
     * 题目答案
     */
    private String subjectAnswer;

    /**
     * 题目所属分类id列表
     */
    private List<Long> categoryIds;

    /**
     * 题目所属标签id列表
     */
    private List<Long> labelIds;

    /**
     * 题目答案选项列表
     */
    private List<SubjectOptionDto> optionList;

    /**
     * 题目所属分类id
     */
    private Long categoryId;

    /**
     * 题目所属标签id
     */
    private Long labelId;

    /**
     * 题目所属标签名称列表
     */
    private List<String> labelNameList;

    /**
     * 第几页
     */
    private Integer pageNo;

    /**
     * 每页多少数据
     */
    private Integer pageSize;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 创建人昵称
     */
    private String createUser;

    /**
     * 创建人头像
     */
    private String createUserAvatar;

    /**
     * 题目数量
     */
    private Integer subjectCount;

    /**
     * 是否被当前用户点赞
     */
    private Boolean liked;

    /**
     * 当前题目点赞的数量
     */
    private Integer likedCount;

    /**
     * 下一题
     */
    private Long nextSubjectId;

    /**
     * 上一题
     */
    private Long lastSubjectId;

    /**
     * 要排除题目的id列表
     */
    private List<Long> excludeSubjectIds;

    /**
     * 分类与标签组合的ids
     */
    private List<String> assembleIds;
}

