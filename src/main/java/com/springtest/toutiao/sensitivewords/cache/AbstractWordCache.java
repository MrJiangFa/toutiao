package com.springtest.toutiao.sensitivewords.cache;

import com.springtest.toutiao.sensitivewords.event.CacheChangedEvent;
import com.springtest.toutiao.sensitivewords.event.CacheChangedListener;
import com.springtest.toutiao.sensitivewords.log.ApplicationLogging;
import com.springtest.toutiao.sensitivewords.model.SensitiveWords;

import java.util.List;

public abstract class AbstractWordCache extends ApplicationLogging implements WordsCache, CacheChangedListener {
    private String listenerName;

    public AbstractWordCache(String listenerName) {
        this.listenerName = listenerName;
    }

    public String getListenerName() {
        return listenerName;
    }
    @Override
    public void setDataSource(Object dataSource) {
        debug("{}: bindDataSource: {}", listenerName, dataSource);
    }

    @Override
    public boolean init() throws Exception {
        debug("{}: init word cache", listenerName);

        return true;
    }
    @Override
    public boolean put(SensitiveWords words) throws Exception {
        debug("{}: put word: {}", listenerName, words);

        return true;
    }

    @Override
    public boolean put(List<SensitiveWords> words) throws Exception {
        debug("{}: put word list: {}", listenerName, words);

        return true;
    }

    @Override
    public List<SensitiveWords> get() throws Exception {
        debug("{}: get word list", listenerName);

        return null;
    }

    @Override
    public boolean update(SensitiveWords word) throws Exception {
        debug("{}: update word: {}", listenerName, word);

        return true;
    }

    @Override
    public boolean remove(SensitiveWords words) throws Exception {
        debug("{}: remove word: {}", listenerName, words);

        return false;
    }

    @Override
    public boolean refresh() throws Exception {
        debug("{}: refresh word cache", listenerName);

        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleChangedEvent(CacheChangedEvent event) throws Exception {

        this.init();

        switch (event.getAction()) {

            case PUT:
                this.put((SensitiveWords) event.getSource());
                break;

            case PUT_LIST:
                this.put((List<SensitiveWords>) event.getSource());
                break;

            case REMOVE:
                this.remove((SensitiveWords) event.getSource());
                break;

            case UPDATE:
                this.update((SensitiveWords) event.getSource());
                break;

            case REFRESH:
                this.refresh();
                break;

            default:
                throw new UnsupportedOperationException();
        }
    }
}
