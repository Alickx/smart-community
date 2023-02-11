package cn.goroute.smart.common.util;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
*@author: Alickx
*@Date: 2023/02/11 11:52:18
*@Description:
*/
public class TransmittableThreadLocalUtil {

	private static final TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();


	public static void set(String value) {
		threadLocal.set(value);
	}

	public static String get() {
		return threadLocal.get();
	}

	public static void remove() {
		threadLocal.remove();
	}


}
