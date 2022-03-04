/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package cn.goroute.smart.common.utils;

/**
 * 自定义异常
 *
 * @author Mark sunlightcs@gmail.com
 */
public class RRException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String message;
    private int code = 500;
    
    public RRException(String msg) {
		super(msg);
		this.message = msg;
	}
	
	public RRException(String msg, Throwable e) {
		super(msg, e);
		this.message = msg;
	}
	
	public RRException(String msg, int code) {
		super(msg);
		this.message = msg;
		this.code = code;
	}
	
	public RRException(String msg, int code, Throwable e) {
		super(msg, e);
		this.message = msg;
		this.code = code;
	}

	public String getMsg() {
		return message;
	}

	public void setMsg(String msg) {
		this.message = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
