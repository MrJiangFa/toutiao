package com.springtest.toutiao.sensitivewords.filter;

import com.springtest.toutiao.sensitivewords.cache.JvmWordsCache;
import com.springtest.toutiao.sensitivewords.cache.RedisWordsCache;
import com.springtest.toutiao.sensitivewords.log.ApplicationLogging;

public abstract class AbstractSensitiveWordsFilter extends ApplicationLogging implements SensitiveWordsFilter {

    private volatile static boolean HAS_INIT_WORDS_CACHE = false;

    public void initAll() throws Exception {
        if (!HAS_INIT_WORDS_CACHE) {
            debug("初始化所有缓存");
            RedisWordsCache.getInstance().init();
            JvmWordsCache.getInstance().init();

            this.init();
        } else {
            debug("缓存已被初始化，无需重复执行！");
        }
    }

    public void refreshAll() throws Exception {

        debug("刷新所有缓存");
        RedisWordsCache.getInstance().refresh();
        JvmWordsCache.getInstance().refresh();

        this.refresh();
    }

    public abstract void init() throws RuntimeException;

    public abstract void refresh() throws RuntimeException;

    public abstract void destroy() throws RuntimeException;
}
