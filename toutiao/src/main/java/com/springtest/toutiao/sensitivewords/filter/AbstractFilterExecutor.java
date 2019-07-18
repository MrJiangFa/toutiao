package com.springtest.toutiao.sensitivewords.filter;

import com.springtest.toutiao.sensitivewords.event.CacheChangedEvent;
import com.springtest.toutiao.sensitivewords.event.CacheChangedListener;
import com.springtest.toutiao.sensitivewords.model.SensitiveWords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 抽象敏感词过滤执行器，提供过滤的缓存初始化、刷新、销毁基础封装
 */
public abstract class AbstractFilterExecutor<T> extends AbstractSensitiveWordsFilterSupport implements CacheChangedListener {

    protected T cacheNodes;
    private String listenerName;

    public AbstractFilterExecutor(String listenerName) {
        this.listenerName = listenerName;
    }

    @Override
    public String getListenerName() {
        return listenerName;
    }

    /**
     * 分词数据对象模型
     *
     * @return 根模型
     */
    protected abstract T getCacheNodes();

    /**
     * 添加分词
     *
     * @throws RuntimeException
     */
    protected abstract boolean put(String word) throws RuntimeException;

    public boolean put(SensitiveWords word) throws RuntimeException {
        trace("{}: put数据 {}", getListenerName(), word.getWord());

        return put(word.getWord());
    }

    public void init(String wordsFileName) throws RuntimeException {
        cacheNodes = this.getCacheNodes();

        BufferedReader reader = null;
        try {
            reader = readDic(wordsFileName);

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                put(line);
            }

            info("{}: 从本地词库加载数据：", getListenerName());
        } catch (Exception e) {
            error("{}: 从本地词库加载数据异常：", getListenerName(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void init() throws RuntimeException {
        try {
            if (cacheNodes == null) {
                debug("{}: 初始化数据", getListenerName());
                refresh();
            } else {
                debug("{}: 已初始化数据，无需重复执行", getListenerName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void refresh() throws RuntimeException {
        debug("{}: 刷新数据", getListenerName());

        try {
            cacheNodes = this.getCacheNodes();
			/*
			List<SensitiveWords> list = JvmWordsCache.getInstance().get();
			for (SensitiveWords word : list) {
				put(word);
			}
			*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() throws RuntimeException {
        debug("{}: 销毁数据", getListenerName());

        try {
            cacheNodes = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     *
     * @param wordsFileName 文件名
     * @return 敏感词集合
     * @throws Exception
     * @author hoojo
     * @createDate 2018年2月8日 下午6:27:28
     */
    protected BufferedReader readDic(String wordsFileName) throws Exception {

        try {

            InputStreamReader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream(wordsFileName), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);

            return bufferedReader;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void handleChangedEvent(CacheChangedEvent event) throws Exception {

        this.init();
        switch (event.getAction()) {

            case PUT:
                this.put((SensitiveWords) event.getSource());
                break;

            case PUT_LIST:
                this.refresh();
                break;

            case REMOVE:
                this.refresh();
                break;

            case UPDATE:
                this.refresh();
                break;

            case REFRESH:
                this.refresh();
                break;

            default:
                throw new UnsupportedOperationException();
        }
    }
}
