package com.springtest.toutiao.sensitivewords.filter.tire.executor;

import com.springtest.toutiao.sensitivewords.filter.AbstractFilterExecutor;
import com.springtest.toutiao.util.JedisAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * tire tree 算法脱敏词库支持类
 */
@Service
public final class TireTreeFilterExecutor extends AbstractFilterExecutor<TireTreeNode> {
    @Autowired
    JedisAdapter jedisAdapter;

    private TireTreeFilterExecutor() {
        super("tire tree 算法脱敏支持类");
    }

    private static class SingleFactory {
        private static final TireTreeFilterExecutor INSTANCE = new TireTreeFilterExecutor();
    }

    public static final TireTreeFilterExecutor getInstance() {
        return SingleFactory.INSTANCE;
    }

    @Override
    protected TireTreeNode getCacheNodes() {
        return new TireTreeNode(' ');
    }

    /**
     * 判断一段文字包含敏感词语，支持敏感词结果回调
     */
    protected boolean processor(boolean partMatch, String content, Callback callback) throws RuntimeException {

        if (StringUtils.isBlank(content)) {
            return false;
        }

        content = StringUtils.trim(content);
        if (content.length() < 2) {
            return false;
        }

        for (int index = 0; index < content.length(); index++) {
            char fisrtChar = content.charAt(index);

            TireTreeNode node = cacheNodes.find(fisrtChar);
            if (node == null || node.isLeaf()) {
                continue;
            }

            int charCount = 1;
            for (int i = index + 1; i < content.length(); i++) {
                char wordChar = content.charAt(i);

                node = node.find(wordChar);
                if (node != null) {
                    charCount++;
                } else {
                    break;
                }

                if (partMatch && node.isWord()) {
                    if (callback.call(StringUtils.substring(content, index, index + charCount))) {
                        return true;
                    }
                    break;
                } else if (node.isWord()) {
                    if (callback.call(StringUtils.substring(content, index, index + charCount))) {
                        return true;
                    }
                }

                if (node.isLeaf()) {
                    break;
                }
            }

            if (partMatch) {
                index += charCount;
            }
        }
        return false;
    }

    @Override
    protected boolean put(String word) throws RuntimeException {
        if (StringUtils.isBlank(word)) {
            return false;
        }

        word = StringUtils.trim(word);
        if (word.length() < 2) {
            return false;
        }

        char fisrtChar = word.charAt(0);
        TireTreeNode node = cacheNodes.find(fisrtChar);
        if (node == null) {
            node = new TireTreeNode(fisrtChar);
            cacheNodes.addChild(node);
        }

        for (int i = 1; i < word.length(); i++) {
            char nextChar = word.charAt(i); // 转换成char型

            TireTreeNode nextNode = null;
            if (!node.isLeaf()) {
                nextNode = node.find(nextChar);
            }
            if (nextNode == null) {
                nextNode = new TireTreeNode(nextChar);
            }

            node.addChild(nextNode);
            node = nextNode;

            if (i == word.length() - 1) {
                node.setWord(true);
            }
        }
        return true;
    }
//
//    public static void main(String[] args) {
//        TireTreeFilterExecutor.getInstance().init();
//        TireTreeFilterExecutor.getInstance().put("中国人");
//        TireTreeFilterExecutor.getInstance().put("中国男人");
//        TireTreeFilterExecutor.getInstance().put("中国人民");
//        TireTreeFilterExecutor.getInstance().put("中国");
//        TireTreeFilterExecutor.getInstance().put("人民");
//        TireTreeFilterExecutor.getInstance().put("中间");
//        TireTreeFilterExecutor.getInstance().put("女人");
//
//        String content = "我们中国人都是好人，在中国人民中间有男人和女人。中国男人很惨，他们长期被压迫。";
//        System.out.println(TireTreeFilterExecutor.getInstance().contains(true, content));
//        System.out.println(TireTreeFilterExecutor.getInstance().contains(false, content));
//        System.out.println(TireTreeFilterExecutor.getInstance().getWords(true, content));
//        System.out.println(TireTreeFilterExecutor.getInstance().getWords(false, content));
//        System.out.println(TireTreeFilterExecutor.getInstance().filter(false, content, '*'));
////		System.out.println(TireTreeFilterExecutor.getInstance().highlight(false, content));
//    }
}
