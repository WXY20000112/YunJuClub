package com.wxy.practice.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSetVO
 * @author: 32115
 * @create: 2024-06-04 11:57
 */
@Data
public class PracticeSetVO implements Serializable {

    /**
     * 套题id
     */
    private Long setId;

    /**
     * 套题名称
     */
    private String setName;

    /**
     * 套题热度
     */
    private Integer setHeat;

    /**
     * 套题描述
     */
    private String setDesc;
}
