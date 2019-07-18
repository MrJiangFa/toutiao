package com.springtest.toutiao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 以hashMap结构实现
 */
public class SensitiveWordFilter {

    public static class TrieNode {
        private char root;
        private boolean isEnd;
        private List<TrieNode> nexts = new ArrayList<>();

        TrieNode(char root) {
            this.root = root;
        }
    }

    public static class TrieTree {

    }

    public static final HashMap<String, TrieTree> map = new HashMap<>();

}
