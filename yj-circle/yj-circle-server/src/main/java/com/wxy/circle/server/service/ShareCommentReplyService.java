package com.wxy.circle.server.service;

import com.mybatisflex.core.service.IService;
import com.wxy.circle.api.req.GetShareCommentReq;
import com.wxy.circle.api.req.RemoveShareCommentReq;
import com.wxy.circle.api.req.SaveShareCommentReplyReq;
import com.wxy.circle.api.vo.ShareCommentReplyVO;
import com.wxy.circle.server.entity.ShareCommentReply;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: ShareCommentReplyService
 * @author: 32115
 * @create: 2024-06-09 16:31
 */
public interface ShareCommentReplyService extends IService<ShareCommentReply> {

    // 发布评论
    Boolean insertShareCommentReply(SaveShareCommentReplyReq req);

    // 删除评论
    Boolean removeComment(RemoveShareCommentReq req);

    // 获取评论列表
    List<ShareCommentReplyVO> listComment(GetShareCommentReq req);
}
