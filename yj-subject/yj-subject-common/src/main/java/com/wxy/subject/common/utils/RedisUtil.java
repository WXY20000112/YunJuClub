package com.wxy.subject.common.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil工具类
*
 * @author: ChickenWing
 * @date: 2023/1/15
 */
@Component
@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 构建缓存key
     */
    public String buildKey(String cacheKeySeparator, String... strObjs) {
        return String.join(cacheKeySeparator, strObjs);
    }

    /**
     * 是否存在key
     */
    public boolean exist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除key
     */
    public boolean del(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 设置key无过期时间
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 删除key有过期时间
     */
    public boolean setNx(String key, String value, Long time, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit));
    }

    /**
     * 获取key
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public Boolean zAdd(String key, String value, Long score) {
        return redisTemplate.opsForZSet().add(key, value, Double.parseDouble(String.valueOf(score)));
    }

    public Long countZset(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    public Set<String> rangeZset(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public Long removeZset(String key, Object value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }

    public void removeZsetList(String key, Set<String> value) {
        value.forEach((val) -> redisTemplate.opsForZSet().remove(key, val));
    }

    public Double score(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public Set<String> rangeByScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScore(key,
                Double.parseDouble(String.valueOf(start)),
                Double.parseDouble(String.valueOf(end)));
    }

    public Object addScore(String key, Object obj, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, obj, score);
    }

    public Object rank(String key, Object obj) {
        return redisTemplate.opsForZSet().rank(key, obj);
    }

    public Set<ZSetOperations.TypedTuple<String>> rankWithScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    public void setHash(String key, String hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    public Integer getHashCount(String key) {
        return Integer.valueOf(String.valueOf(redisTemplate.opsForValue().get(key)));
    }

    public void incrementHashCount(String key, Integer count) {
        redisTemplate.opsForValue().increment(key, count);
    }

    public Map<Object, Object> getHashAndDelete(String key) {
        Map<Object, Object> map = new HashMap<>();
        try (Cursor<Map.Entry<Object, Object>> cursor =
                     redisTemplate.opsForHash().scan(key, ScanOptions.NONE)) {
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                Object hashKey = entry.getKey();
                Object value = entry.getValue();
                map.put(hashKey, value);
                redisTemplate.opsForHash().delete(key, hashKey);
            }
        }
        return map;
    }
}