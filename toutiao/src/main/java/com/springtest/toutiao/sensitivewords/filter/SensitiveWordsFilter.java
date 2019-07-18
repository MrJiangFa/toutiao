package com.springtest.toutiao.sensitivewords.filter;

import java.util.Set;

public interface SensitiveWordsFilter {
    /**
     * @param partMatch：是否支持匹配词语的一部分
     * @param content：待匹配内容
     * @return
     * @throws RuntimeException
     */
    public boolean contains(boolean partMatch, String content) throws RuntimeException;

    /**
     * 返回匹配到的敏感词
     * @param partMatch：是否支持部分匹配
     * @param content：待匹配内容
     * @return：返回匹配的敏感词集合
     * @throws RuntimeException
     */
    public Set<String> getWords(boolean partMatch, String content) throws RuntimeException;

    /**
     * 过滤敏感词，并把敏感词替换为指定字符串
     * @param partMatch：是否支持部分匹配
     * @param content：待匹配语句
     * @param replaceChar：替换字符
     * @return
     * @throws RuntimeException
     */
    public String filter(boolean partMatch,String content,char replaceChar) throws RuntimeException;
}
