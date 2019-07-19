package com.springtest.toutiao.util;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class SessionContext {
    private static SessionContext instance;
    private HashMap<String, HttpSession> sessionMap;

    private SessionContext() {
        this.sessionMap = new HashMap<>();
    }

    public static SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    public synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }

    public synchronized void deleteSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }
}
