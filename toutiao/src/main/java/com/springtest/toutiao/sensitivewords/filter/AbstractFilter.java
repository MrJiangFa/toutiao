package com.springtest.toutiao.sensitivewords.filter;

import com.springtest.toutiao.sensitivewords.event.WordsCacheContext;

import java.util.Set;

/**
 * 抽象过滤脱敏接口实现
 *
 */
public abstract class AbstractFilter extends AbstractSensitiveWordsFilter {

	private AbstractFilterExecutor<?> executor;
	
	public AbstractFilter(AbstractFilterExecutor<?> executor) {
		
		WordsCacheContext.getInstance().register(this.executor);
		
		this.executor = executor;
	}
	
	@Override
	public boolean contains(boolean partMatch, String content) throws RuntimeException {
		
		return executor.contains(partMatch, content);
	}

	@Override
	public Set<String> getWords(boolean partMatch, String content) throws RuntimeException {
		
		return executor.getWords(partMatch, content);
	}


	@Override
	public String filter(boolean partMatch, String content, char replaceChar) throws RuntimeException {
		
		return executor.filter(partMatch, content, replaceChar);
	}

	@Override
	public void init() throws RuntimeException {
		
		executor.init();
	}

	@Override
	public void refresh() throws RuntimeException {

		executor.refresh();
	}

	@Override
	public void destroy() throws RuntimeException {

		executor.destroy();
	}
}
