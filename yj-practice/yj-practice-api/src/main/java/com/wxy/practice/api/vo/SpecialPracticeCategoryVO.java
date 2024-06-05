package com.wxy.practice.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SpecialPracticeCategoryVO
 * @author: 32115
 * @create: 2024-06-02 17:35
 */
@Data
public class SpecialPracticeCategoryVO implements Serializable {

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类下标签信息
     */
    private List<SpecialPracticeLabelVO> labelList;
}
