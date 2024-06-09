package com.wxy.circle.api.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 32115
 * @description: 动态内容信息
 * @date: 2024/6/9
 */
@Data
public class SaveMomentCircleReq implements Serializable {

    /**
     * 圈子ID
     */
    private Long circleId;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 动态图片内容
     */
    private List<String> picUrlList;

}
