package com.wxy.circle.server.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.circle.server.entity.ShareCommentReply;
import com.wxy.circle.server.mapper.ShareCommentReplyMapper;
import com.wxy.circle.server.service.ShareCommentReplyService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: ShareCommentReplyServiceImpl
 * @author: 32115
 * @create: 2024-06-09 16:31
 */
@Service
public class ShareCommentReplyServiceImpl
        extends ServiceImpl<ShareCommentReplyMapper, ShareCommentReply>
        implements ShareCommentReplyService {
}
