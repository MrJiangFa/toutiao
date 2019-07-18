package com.springtest.toutiao.sensitivewords.event;

import java.util.EventObject;

/**
 * 缓存修改事件
 *
 */
public class CacheChangedEvent extends EventObject {

	private static final long serialVersionUID = 6217432220420884817L;

	public enum Action {
		PUT, PUT_LIST, REMOVE, REFRESH, UPDATE;
	}
	
	private Action action;
	
	public CacheChangedEvent(Object source, Action action) {
		super(source);
		
		this.action = action;
	}

	public void doEvent() {
		System.out.println("触发事件：" + action + "，" + this.getSource());
	}
	
	public Action getAction() {
		return action;
	}
}
