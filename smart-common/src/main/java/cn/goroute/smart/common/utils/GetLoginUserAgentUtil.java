package cn.goroute.smart.common.utils;

import cn.hutool.extra.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/21:21
 * @Description:
 */
public class GetLoginUserAgentUtil {

    /**
     * 获取登录用户系统信息
     * @param request 请求
     * @return 登录用户系统信息
     */
    public static String getOs(HttpServletRequest request){

        String userAgent = ServletUtil.getHeader(request, "User-Agent", "UTF-8");

        //根据userAgent判断系统类型
        if (userAgent.contains("Windows")) {
            return "Windows";
        }
        if (userAgent.contains("Mac")) {
            return "Mac";
        }
        if (userAgent.contains("X11")) {
            return "Unix";
        }
        if (userAgent.contains("Android")) {
            return "Android";
        }
        if (userAgent.contains("Linux")) {
            return "Linux";
        }
        return "UnKnown, More-Info: " + userAgent;
    }

    public static String getBrowser(HttpServletRequest request){

        //获取用户浏览器类型
        String userAgent = ServletUtil.getHeader(request, "User-Agent", "UTF-8");
        if (userAgent.contains("MSIE")) {
            return "IE";
        }
        if (userAgent.contains("Chrome")) {
            return "Chrome";
        }
        if (userAgent.contains("Firefox")) {
            return "Firefox";
        }
        if (userAgent.contains("Safari")) {
            return "Safari";
        }
        if (userAgent.contains("Opera")) {
            return "Opera";
        }
        return "UnKnown, More-Info: " + userAgent;

    }

}
