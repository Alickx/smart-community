package cn.goroute.smart.member.service;

import cn.goroute.smart.common.entity.pojo.Member;
import cn.goroute.smart.common.entity.vo.MemberInfoUpdateVo;
import cn.goroute.smart.common.entity.vo.MemberLoginVo;
import cn.goroute.smart.common.entity.vo.MemberRegisterVo;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
public interface MemberService extends IService<Member> {

    Result updateMemberInfo(MemberInfoUpdateVo memberVO);

    /**
     * 用户登陆
     * @param memberLoginVO 用户登陆信息
     * @param request 请求
     * @return 登陆结果
     */
    Result login(MemberLoginVo memberLoginVO, HttpServletRequest request);

    /**
     * 用户注册
     * @param memberRegisterVO 用户注册信息
     * @return 注册结果
     */
    Result register(MemberRegisterVo memberRegisterVO);

}


