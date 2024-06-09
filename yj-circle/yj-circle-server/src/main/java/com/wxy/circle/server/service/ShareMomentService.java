package com.wxy.circle.server.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.wxy.circle.api.req.GetShareMomentReq;
import com.wxy.circle.api.req.RemoveShareMomentReq;
import com.wxy.circle.api.req.SaveMomentCircleReq;
import com.wxy.circle.api.vo.ShareMomentVO;
import com.wxy.circle.server.entity.ShareMoment;

/**
 * @program: YunJuClub-Flex
 * @description: ShareMomentService
 * @author: 32115
 * @create: 2024-06-09 16:34
 */
public interface ShareMomentService extends IService<ShareMoment> {

    // 发布圈子内容
    Boolean insertShareMoment(SaveMomentCircleReq req);

    // 分页获取圈子内容
    Page<ShareMomentVO> getMoments(GetShareMomentReq req);

    // 删除圈子内容
    Boolean removeMoment(RemoveShareMomentReq req);
}
