package com.springtest.toutiao.sensitivewords.filter;


import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractSensitiveWordsFilterSupport extends AbstractSensitiveWordsFilter {
    /**
     * 匹配到敏感词的回调接口
     */
    protected interface Callback{
        boolean call(String word);
    }

    /**
     * 判断一段文字包含敏感词，支持敏感词结果回调
     * @param partMatch
     * @param content
     * @param callback
     * @return
     * @throws RuntimeException
     */
    protected abstract boolean processor(boolean partMatch,String content,Callback callback) throws RuntimeException;

    @Override
    public boolean contains(boolean partMatch, String content) throws RuntimeException {
        return processor(partMatch, content, new Callback() {
            @Override
            public boolean call(String word) {
                return true; // 有敏感词立即返回
            }
        });
    }

    @Override
    public Set<String> getWords(boolean partMatch, String content) throws RuntimeException {
        final Set<String> words = new HashSet();

        processor(partMatch, content, new Callback() {
            @Override
            public boolean call(String word) {
                words.add(word);
                return false; // 继续匹配后面的敏感词
            }
        });

        return words;
    }

    @Override
    public String filter(boolean partMatch, String content, char replaceChar) throws RuntimeException {
        Set<String> words = this.getWords(partMatch, content);

        Iterator<String> iter = words.iterator();
        while (iter.hasNext()) {
            String word = iter.next();
            content = content.replaceAll(word, Strings.repeat(String.valueOf(replaceChar), word.length()));
        }

        return content;
    }
}
