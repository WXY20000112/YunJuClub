package com.wxy.practice.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: SpecialPracticeLabelVO
 * @author: 32115
 * @create: 2024-06-02 17:36
 */
@Data
public class SpecialPracticeLabelVO implements Serializable {

    /**
     * 标签ID
     */
    private Long id;

    /**
     * 分类id-标签ID 用于确定一个唯一的标签信息
     */
    private String assembleId;

    /**
     * 标签名称
     */
    private String labelName;
}
