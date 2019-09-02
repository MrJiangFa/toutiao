package com.springtest.toutiao.sensitivewords.cache;

import com.springtest.toutiao.sensitivewords.model.SensitiveWords;

import java.util.List;

/**
 * 敏感词库缓存
 */
public interface WordsCache {
    public void setDataSource(Object dataSource);

    public boolean init() throws Exception;

    public boolean put(SensitiveWords words) throws Exception;

    public boolean put(List<SensitiveWords> words) throws Exception;

    public List<SensitiveWords> get() throws Exception;

    public boolean remove(SensitiveWords words) throws Exception;

    public boolean refresh() throws Exception;

    boolean update(SensitiveWords word) throws Exception;
}
