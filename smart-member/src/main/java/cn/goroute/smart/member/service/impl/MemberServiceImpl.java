package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.common.entity.dto.MemberDto;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.entity.pojo.Member;
import cn.goroute.smart.member.entity.vo.MemberInfoUpdateVo;
import cn.goroute.smart.member.mapper.MemberMapper;
import cn.goroute.smart.member.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("memberService")
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Resource
    AuthService authService;

    /**
     * 更新用户展示信息
     *
     * @param memberVO 用户展示信息VO
     * @return 更新结果
     */
    @Override
    public Result updateMemberInfo(MemberInfoUpdateVo memberVO) {

        //检查用户名是否存在违禁词
//        Boolean nickNameCheckResult = illegalTextCheckUtil.checkText(memberVO.getNickName());
//        Boolean introCheckResult = illegalTextCheckUtil.checkText(memberVO.getIntro());
//        if (nickNameCheckResult || introCheckResult) {
//            return Result.error("用户名或简介包含违禁词");
//        }
        Member member = new Member();
        long loginId = authService.getLoginUid();
        BeanUtils.copyProperties(memberVO, member);
        member.setId(loginId);
        int result = memberMapper.updateById(member);
        MemberDto dto = new MemberDto();
        BeanUtils.copyProperties(member, dto);
        if (result > 0) {
            return Result.ok("用户信息更新成功！").put("data", dto);
        } else {
            log.error("用户信息更新失败！用户信息=>{}", memberVO);
            throw new ServiceException("用户信息更新失败");
        }
    }
}