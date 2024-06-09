package com.wxy.circle.api.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author: 32115
 * @description: 消息表
 * @date: 2024/6/9
 */
@Data
public class ShareMessageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 消息内容
     */
    private Map<String, Object> content;

    /**
     * 创建时间
     */
    private Date createdTime;

}
