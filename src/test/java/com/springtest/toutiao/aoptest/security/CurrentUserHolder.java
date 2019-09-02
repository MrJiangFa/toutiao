package com.springtest.toutiao.aoptest.security;

public class CurrentUserHolder {
    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static String get() {
        return holder.get() == null ? "Unknown" : holder.get();

    }

    public static void set(String user) {
        holder.set(user);
    }
}
