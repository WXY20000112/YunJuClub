package com.wxy.circle.server.service;

import com.mybatisflex.core.service.IService;
import com.wxy.circle.server.entity.SensitiveWords;

/**
 * @program: YunJuClub-Flex
 * @description: SensitiveWordsService
 * @author: 32115
 * @create: 2024-06-09 16:26
 */
public interface SensitiveWordsService extends IService<SensitiveWords> {

    // 删除
    Boolean deleteById(Long id);

    // 添加
    Boolean saveSensitiveWords(String words, Integer type);
}
