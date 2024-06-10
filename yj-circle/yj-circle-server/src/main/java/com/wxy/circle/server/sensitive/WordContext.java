package com.wxy.circle.server.sensitive;

import com.alibaba.fastjson2.JSON;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.wxy.circle.server.entity.SensitiveWords;
import com.wxy.circle.server.service.SensitiveWordsService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.wxy.circle.server.entity.table.SensitiveWordsTableDef.SENSITIVE_WORDS;

/**
 * 词库上下文环境
 * <p>
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 *
 * @author minghu.zhang
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Slf4j
@Component
public class WordContext {

    /**
     * 敏感词字典
     * -- GETTER --
     *  获取初始化的敏感词列表
     *
     */
    @Getter
    private final Map wordMap = new HashMap(1024);

    /**
     * 是否已初始化
     */
    private boolean init;

    private long addLastId;

    public WordContext(SensitiveWordsService service) {
        clearDelData(service);
        Set<String> black = new HashSet<>();
        Set<String> white = new HashSet<>();
        List<SensitiveWords> list = service.list();
        for (SensitiveWords words : list) {
            if (words.getType() == 1) {
                black.add(words.getWords());
            } else {
                white.add(words.getWords());
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            this.addLastId = list.getLast().getId();
        }
        initKeyWord(black, white);
        reloadWord(service);
    }

    private void clearDelData(SensitiveWordsService service) {
        QueryWrapper query = QueryWrapper.create()
                .select()
                .from(SENSITIVE_WORDS)
                .where(SENSITIVE_WORDS.IS_DELETED.eq(1));
        LogicDeleteManager.execWithoutLogicDelete(()->
                service.remove(query)
        );
    }

    private void reloadWord(SensitiveWordsService service) {

        // 创建一个单线程的定时线程池
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        // 创建一个Runnable任务
        Runnable task = () -> {
            try {
                addNewWords(service);
                removeDelWords(service);
            } catch (Exception e) {
                log.error("Sensitive words task error", e);
            }
        };
        // 定时执行任务，初始延迟0，之后每分钟执行一次
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.DAYS);

    }

    private void removeDelWords(SensitiveWordsService service) {
        QueryWrapper query = QueryWrapper.create()
                .select()
                .from(SENSITIVE_WORDS)
                .where(SENSITIVE_WORDS.IS_DELETED.eq(1));
        List<SensitiveWords> list = LogicDeleteManager.execWithoutLogicDelete(() ->
                service.list(query)
        );
        if (!CollectionUtils.isEmpty(list)) {
            log.info("removeDelWords {}", JSON.toJSON(list));
            Result result = getResult(list);
            removeWord(result.black(), WordType.BLACK);
            removeWord(result.white(), WordType.WHITE);
        }
    }

    private void addNewWords(SensitiveWordsService service) {
        QueryWrapper query = QueryWrapper.create()
                .select()
                .from(SENSITIVE_WORDS)
                .where(SENSITIVE_WORDS.ID.gt(addLastId));
        List<SensitiveWords> list = service.list(query);
        if (!CollectionUtils.isEmpty(list)) {
            log.info("addNewWords {}", JSON.toJSON(list));
            this.addLastId = list.getLast().getId();
            Result result = getResult(list);
            addWord(result.black(), WordType.BLACK);
            addWord(result.white(), WordType.WHITE);
        }
    }

    private static Result getResult(List<SensitiveWords> list) {
        Set<String> black = new HashSet<>();
        Set<String> white = new HashSet<>();
        for (SensitiveWords words : list) {
            if (words.getType() == 1) {
                black.add(words.getWords());
            } else {
                white.add(words.getWords());
            }
        }
        return new Result(black, white);
    }

    private record Result(Set<String> black, Set<String> white) {
    }

    /**
     * 初始化
     */
    private synchronized void initKeyWord(Set<String> black, Set<String> white) {
        try {
            if (!init) {
                // 将敏感词库加入到HashMap中
                addWord(black, WordType.BLACK);
                // 将非敏感词库也加入到HashMap中
                addWord(white, WordType.WHITE);
            }
            init = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = { isEnd = 0 国 = {<br>
     * isEnd = 1 人 = {isEnd = 0 民 = {isEnd = 1} } 男 = { isEnd = 0 人 = { isEnd = 1 }
     * } } } 五 = { isEnd = 0 星 = { isEnd = 0 红 = { isEnd = 0 旗 = { isEnd = 1 } } } }
     */
    public void addWord(Collection<String> wordList, WordType wordType) {
        if (CollectionUtils.isEmpty(wordList)) {
            return;
        }
        Map nowMap;
        Map<String, String> newWorMap;
        // 迭代keyWordSet
        for (String key : wordList) {
            nowMap = wordMap;
            for (int i = 0; i < key.length(); i++) {
                // 转换成char型
                char keyChar = key.charAt(i);
                // 获取
                Object wordMap = nowMap.get(keyChar);
                // 如果存在该key，直接赋值
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                } else {
                    // 不存在则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<>(4);
                    // 不是最后一个
                    newWorMap.put("isEnd", String.valueOf(EndType.HAS_NEXT.ordinal()));
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    // 最后一个
                    nowMap.put("isEnd", String.valueOf(EndType.IS_END.ordinal()));
                    nowMap.put("isWhiteWord", String.valueOf(wordType.ordinal()));
                }
            }
        }
    }

    /**
     * 在线删除敏感词
     *
     * @param wordList 敏感词列表
     * @param wordType 黑名单 BLACk，白名单WHITE
     */
    public void removeWord(Collection<String> wordList, WordType wordType) {
        if (CollectionUtils.isEmpty(wordList)) {
            return;
        }
        Map nowMap;
        for (String key : wordList) {
            List<Map> cacheList = new ArrayList<>();
            nowMap = wordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);

                Object map = nowMap.get(keyChar);
                if (map != null) {
                    nowMap = (Map) map;
                    cacheList.add(nowMap);
                } else {
                    return;
                }

                if (i == key.length() - 1) {
                    char[] keys = key.toCharArray();
                    boolean cleanable = false;
                    char lastChar = 0;
                    for (int j = cacheList.size() - 1; j >= 0; j--) {
                        Map cacheMap = cacheList.get(j);
                        if (j == cacheList.size() - 1) {
                            if (String.valueOf(WordType.BLACK.ordinal()).equals(cacheMap.get("isWhiteWord"))) {
                                if (wordType == WordType.WHITE) {
                                    return;
                                }
                            }
                            if (String.valueOf(WordType.WHITE.ordinal()).equals(cacheMap.get("isWhiteWord"))) {
                                if (wordType == WordType.BLACK) {
                                    return;
                                }
                            }
                            cacheMap.remove("isWhiteWord");
                            cacheMap.remove("isEnd");
                            if (cacheMap.isEmpty()) {
                                cleanable = true;
                                continue;
                            }
                        }
                        if (cleanable) {
                            Object isEnd = cacheMap.get("isEnd");
                            if (String.valueOf(EndType.IS_END.ordinal()).equals(isEnd)) {
                                cleanable = false;
                            }
                            cacheMap.remove(lastChar);
                        }
                        lastChar = keys[j];
                    }

                    if (cleanable) {
                        wordMap.remove(lastChar);
                    }
                }
            }
        }
    }

}
