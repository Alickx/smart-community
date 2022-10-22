package cn.goroute.smart.common.util;

import java.util.concurrent.*;

/**
 * @Author: Alickx
 * @Date: 2022/10/22/23:48
 * @Description:
 */
public class ThreadPoolBuilder {

	private static final RejectedExecutionHandler DEFAULT_REJECT_HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

	/**
	 * cpu核数
	 */
	private static final int CPU = Runtime.getRuntime().availableProcessors();


	/**
	 * create io ThreadPoolExecutor
	 *
	 * @return ThreadPoolExecutor
	 */
	public static IOThreadPoolBuilder ioThreadPoolBuilder() {
		return new IOThreadPoolBuilder();
	}

	/**
	 * create cpu ThreadPoolExecutor
	 *
	 * @return
	 */
	public IOThreadPoolBuilder CPUPool() {
		return new IOThreadPoolBuilder();

	}


	public static class IOThreadPoolBuilder {

		private ThreadFactory threadFactory;

		private RejectedExecutionHandler rejectHandler;


		private int queueSize = -1;

		private int maximumPoolSize = CPU;

		private int keepAliveTime = 120;

		private boolean daemon = false;

		private String threadNamePrefix;


		public int getCorePooSize(int ioTime, int cpuTime) {
			return CPU + (1 + (ioTime / cpuTime));
		}

		public IOThreadPoolBuilder setThreadNamePrefix(String threadNamePrefix) {
			this.threadNamePrefix = threadNamePrefix;
			return this;
		}

		public IOThreadPoolBuilder setDaemon(boolean daemon) {
			this.daemon = daemon;
			return this;
		}


		public IOThreadPoolBuilder setRejectHandler(RejectedExecutionHandler rejectHandler) {
			this.rejectHandler = rejectHandler;
			return this;
		}

		public IOThreadPoolBuilder setQueueSize(int queueSize) {
			this.queueSize = queueSize;
			return this;
		}

		public IOThreadPoolBuilder setMaximumPoolSize(int maximumPoolSize) {
			this.maximumPoolSize = maximumPoolSize;
			return this;
		}

		public IOThreadPoolBuilder setKeepAliveTime(int keepAliveTime) {
			this.keepAliveTime = keepAliveTime;
			return this;
		}


		public ThreadPoolExecutor builder(int ioTime, int cpuTime) {
			BlockingQueue<Runnable> queue;

			if (rejectHandler == null) {
				rejectHandler = DEFAULT_REJECT_HANDLER;
			}
			threadFactory = ThreadPoolUtil.createThreadFactory(this.threadNamePrefix, this.daemon);

			queue = queueSize < 1 ? new LinkedBlockingQueue<>() : new ArrayBlockingQueue<>(queueSize);

			return new ThreadPoolExecutor(getCorePooSize(ioTime, cpuTime), maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, queue, threadFactory, rejectHandler);

		}


	}

}

