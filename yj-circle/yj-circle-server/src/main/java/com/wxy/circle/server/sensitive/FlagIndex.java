package com.wxy.circle.server.sensitive;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 敏感词标记
 *
 * @author minghu.zhang
 */
public class FlagIndex {

    /**
     * 标记结果
     */
    @Setter
    @Getter
    private boolean flag;
    /**
     * 是否黑名单词汇
     */
    private boolean isWhiteWord;
    /**
     * 标记索引
     */
    @Setter
    @Getter
    private List<Integer> index;

    public boolean isWhiteWord() {
        return isWhiteWord;
    }

    public void setWhiteWord(boolean whiteWord) {
        isWhiteWord = whiteWord;
    }
}
