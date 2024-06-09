package com.wxy.circle.server.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.circle.server.entity.SensitiveWords;
import com.wxy.circle.server.mapper.SensitiveWordsMapper;
import com.wxy.circle.server.service.SensitiveWordsService;
import org.springframework.stereotype.Service;

/**
 * @program: YunJuClub-Flex
 * @description: SensitiveWordsServiceImpl
 * @author: 32115
 * @create: 2024-06-09 16:27
 */
@Service
public class SensitiveWordsServiceImpl
        extends ServiceImpl<SensitiveWordsMapper, SensitiveWords>
        implements SensitiveWordsService {
}
