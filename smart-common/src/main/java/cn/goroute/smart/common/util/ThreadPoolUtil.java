package cn.goroute.smart.common.util;

import cn.goroute.smart.common.config.ThreadFactoryConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Author: Alickx
 * @Date: 2022/10/22/23:42
 * @Description: 线程池工具类
 */
public class ThreadPoolUtil {

	public static ThreadFactory createThreadFactory(String threadNamePrefix, boolean daemon) {
		if (threadNamePrefix != null) {
			return new ThreadFactoryConfig(threadNamePrefix, daemon);
		}

		return Executors.defaultThreadFactory();

	}
}
