package com.wxy.circle.server.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.wxy.circle.api.req.GetShareMessageReq;
import com.wxy.circle.api.vo.ShareMessageVO;
import com.wxy.circle.server.entity.ShareMessage;

/**
 * @program: YunJuClub-Flex
 * @description: ShareMessageService
 * @author: 32115
 * @create: 2024-06-09 16:33
 */
public interface ShareMessageService extends IService<ShareMessage> {

    // 查询是否存在未读消息
    Boolean existUnReadMessage();

    // 分页查询消息
    Page<ShareMessageVO> getShareMessagePage(GetShareMessageReq req);

    // 添加评论消息并推送
    void comment(String loginId, String createdBy, Long targetId);

    // 添加回复消息并推送
    void reply(String loginId, String createdBy, Long id);
}
