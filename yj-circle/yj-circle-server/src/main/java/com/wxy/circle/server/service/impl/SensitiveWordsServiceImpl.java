package com.wxy.circle.server.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.entity.SensitiveWords;
import com.wxy.circle.server.mapper.SensitiveWordsMapper;
import com.wxy.circle.server.service.SensitiveWordsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private SensitiveWordsMapper sensitiveWordsMapper;

    /**
     * @author: 32115
     * @description: 保存敏感词
     * @date: 2024/6/10
     * @param: words
     * @param: type
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public Boolean saveSensitiveWords(String words, Integer type) {
        SensitiveWords data = new SensitiveWords();
        data.setType(type);
        data.setWords(words);
        return this.save(data);
    }

    /**
     * @author: 32115
     * @description: 删除敏感词
     * @date: 2024/6/10
     * @param: id
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public Boolean deleteById(Long id) {
        return sensitiveWordsMapper.deleteById(id) > 0;
    }
}
