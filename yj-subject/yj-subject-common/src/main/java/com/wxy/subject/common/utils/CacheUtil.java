package com.wxy.subject.common.utils;

import com.alibaba.fastjson2.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @program: YunJuClub-Flex
 * @description: 缓存工具类
 * @author: 32115
 * @create: 2024-05-26 15:19
 */
@Component
public class CacheUtil<V> {

    /**
     * 初始化本地缓存：private final Cache<String, String> localCache 是一个基于Guava库的本地缓存实例
     * 它限制最大容量为5000个条目
     * 并且在条目写入后10秒自动过期
     * 这意味着缓存会自动管理其大小，过期的数据将被自动移除
     */
    private final Cache<String, String> localCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(10, TimeUnit.SECONDS)
                    .build();

    /**
     * @author: 32115
     * @description: 在缓存中查找数据，如果找到就直接返回，否则计算新数据并存入缓存
     * @date: 2024/5/26
     * @param: cacheKey 用于标识缓存数据的键
     * @param: clazz 表示期望结果列表中每个元素的类型
     * @param: function 一个函数接口，它的apply方法接受一个字符串参数（假设是cacheKey），并返回一个List<V>类型的值。这个函数用于在缓存中找不到数据时，根据cacheKey计算出结果
     * @return: List<V>
     */
    public List<V> getResult(String cacheKey, Class<V> clazz, Function<String, List<V>> function){
        List<V> resultList;
        // 从本地缓存中获取数据
        String content = localCache.getIfPresent(cacheKey);
        // 检查localCache中是否存在与cacheKey对应的缓存数据
        if (StringUtils.isNotBlank(content)){
            // 如果存在，且数据非空，将数据转换为List<V>类型并返回
            resultList = JSON.parseArray(content, clazz);
        } else {
            // 如果缓存中没有找到数据，调用function.apply(cacheKey)来查询结果
            resultList = function.apply(cacheKey);
            if (!CollectionUtils.isEmpty(resultList)){
                // 计算出的结果resultList如果非空，将其序列化为JSON字符串，并存入localCache，以便下次请求时使用
                localCache.put(cacheKey, JSON.toJSONString(resultList));
            }
        }
        // 最后，无论是否使用缓存，都返回resultList
        return resultList;
    }
}
