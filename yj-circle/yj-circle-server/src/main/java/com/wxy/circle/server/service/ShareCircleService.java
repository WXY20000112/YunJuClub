package com.wxy.circle.server.service;

import com.mybatisflex.core.service.IService;
import com.wxy.circle.api.req.RemoveShareCircleReq;
import com.wxy.circle.api.req.SaveShareCircleReq;
import com.wxy.circle.api.req.UpdateShareCircleReq;
import com.wxy.circle.api.vo.ShareCircleVO;
import com.wxy.circle.server.entity.ShareCircle;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: ShareCircleService
 * @author: 32115
 * @create: 2024-06-09 16:29
 */
public interface ShareCircleService extends IService<ShareCircle> {

    // 新增圈子
    Boolean insertShareCircle(SaveShareCircleReq req);

    // 修改圈子
    Boolean updateCircle(UpdateShareCircleReq req);

    // 删除圈子
    Boolean removeCircle(RemoveShareCircleReq req);

    // 获取圈子列表
    List<ShareCircleVO> getShareCircleList();
}
