package cn.goroute.smart.auth.service;

import cn.goroute.smart.auth.entity.vo.MemberLoginVo;
import cn.goroute.smart.auth.entity.vo.MemberRegisterVo;
import cn.goroute.smart.common.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/21:04
 * @Description:
 */
public interface AuthenticationService {
    /**
     * 用户登录
     * @param memberLoginVO 用户登录信息
     * @param request 请求
     * @return 登录结果
     */
    Result login(MemberLoginVo memberLoginVO, HttpServletRequest request);

    /**
     * 用户注册
     * @param memberRegisterVO 用户注册信息
     * @return 注册结果
     */
    Result register(MemberRegisterVo memberRegisterVO);
}
