package cn.goroute.smart.auth.service;

import cn.goroute.smart.common.entity.vo.MemberLoginVo;
import cn.goroute.smart.common.entity.vo.MemberRegisterVo;
import cn.goroute.smart.common.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/21:04
 * @Description:
 */
public interface AuthenticationService {
    Result login(MemberLoginVo memberLoginVO, HttpServletRequest request);

    Result register(MemberRegisterVo memberRegisterVO);
}
