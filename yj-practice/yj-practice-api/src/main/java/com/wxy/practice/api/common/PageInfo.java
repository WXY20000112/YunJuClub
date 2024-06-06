package com.wxy.practice.api.common;

import lombok.Setter;

/**
 * @program: YunJuClub-Flex
 * @description: 分页参数
 * @author: 32115
 * @create: 2024-06-06 17:05
 */
@Setter
public class PageInfo {

    private Integer pageNo;

    private Integer pageSize;

    public Integer getPageNo() {
        if (pageNo == null || pageNo < 1) {
            return 1;
        }
        return pageNo;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1) {
            return 20;
        }
        return pageSize;
    }
}
