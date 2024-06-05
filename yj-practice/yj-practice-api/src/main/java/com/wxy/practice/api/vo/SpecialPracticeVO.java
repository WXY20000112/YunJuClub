package com.wxy.practice.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SpecialPracticeVO
 * @author: 32115
 * @create: 2024-06-02 17:33
 */
@Data
public class SpecialPracticeVO implements Serializable {

    /**
     * 一级分类名称
     */
    private String primaryCategoryName;

    /**
     * 一级分类id
     */
    private Long primaryCategoryId;

    /**
     * 练题分类信息集合
     */
    private List<SpecialPracticeCategoryVO> categoryList;
}
