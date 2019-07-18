package com.springtest.toutiao.sensitivewords.model;

public enum SensitiveWordsType {
    PORN(1, "色情"),
    POLITICS(2, "政治"),
    TERROR(3, "反恐"),
    REACTION(4, "反动"),
    CORRUPTION(5, "贪腐"),
    OTHERS(6, "其他");

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private int code;
    private String name;

    SensitiveWordsType(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
