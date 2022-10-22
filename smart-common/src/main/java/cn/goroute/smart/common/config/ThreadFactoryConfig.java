package cn.goroute.smart.common.config;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Alickx
 * @Date: 2022/10/22/23:43
 * @Description: 自定义线程工厂
 */
public class ThreadFactoryConfig implements ThreadFactory {

	/**
	 * 线程下标
	 */
	private final AtomicLong threadIndex = new AtomicLong(0);
	/**
	 * 线程前缀
	 */
	private final String threadNamePrefix;
	/**
	 * 是否是守护线程
	 */
	private final boolean daemon;

	public ThreadFactoryConfig(final String threadNamePrefix) {
		this(threadNamePrefix, false);
	}

	public ThreadFactoryConfig(final String threadNamePrefix, boolean daemon) {
		this.threadNamePrefix = threadNamePrefix;
		this.daemon = daemon;
	}

	@Override
	public Thread newThread(@NotNull Runnable r) {
		Thread thread = new Thread(r, threadNamePrefix + this.threadIndex.incrementAndGet());
		thread.setDaemon(daemon);
		return thread;
	}
}
