package com.wxy.practice.server.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeRank implements Serializable {

    /**
     * 练习数量
     */
    private Integer count;

    /**
     * 创建人
     */
    private String createdBy;

}