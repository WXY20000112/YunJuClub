package com.wxy.circle.api.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 32115
 * @description: 圈子信息
 * @date: 2024/6/9
 */
@Data
public class UpdateShareCircleReq implements Serializable {

    private Long id;

    /**
     * 圈子名称
     */
    private String circleName;

    /**
     * 圈子图标
     */
    private String icon;

    /**
     * -1为一级,分类ID
     */
    private Long parentId;

}
