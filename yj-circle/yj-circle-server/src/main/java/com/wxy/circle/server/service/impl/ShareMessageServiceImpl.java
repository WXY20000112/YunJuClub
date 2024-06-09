package com.wxy.circle.server.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.circle.server.entity.ShareMessage;
import com.wxy.circle.server.mapper.ShareMessageMapper;
import com.wxy.circle.server.service.ShareMessageService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: ShareMessageServiceImpl
 * @author: 32115
 * @create: 2024-06-09 16:33
 */
@Service
public class ShareMessageServiceImpl
        extends ServiceImpl<ShareMessageMapper, ShareMessage>
        implements ShareMessageService {
}
