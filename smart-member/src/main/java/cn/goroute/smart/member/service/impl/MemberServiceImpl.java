package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.common.dao.MemberBanDao;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.entity.dto.MemberDto;
import cn.goroute.smart.common.entity.pojo.Member;
import cn.goroute.smart.common.entity.vo.MemberInfoUpdateVo;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberService;
import cn.goroute.smart.member.util.IllegalTextCheckUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("memberService")
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberDao, Member> implements MemberService {

    @Autowired
    IllegalTextCheckUtil illegalTextCheckUtil;

    @Autowired
    MemberDao memberDao;

    @Autowired
    MemberBanDao memberBanDao;

    @Autowired
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
        Boolean nickNameCheckResult = illegalTextCheckUtil.checkText(memberVO.getNickName());
        Boolean introCheckResult = illegalTextCheckUtil.checkText(memberVO.getIntro());
        if (nickNameCheckResult || introCheckResult) {
            return Result.error("用户名或简介包含违禁词");
        }
        Member member = new Member();
        long loginId = authService.getLoginUid();
        BeanUtils.copyProperties(memberVO, member);
        member.setUid(loginId);
        int result = memberDao.updateById(member);
        MemberDto dto = new MemberDto(member);
        BeanUtils.copyProperties(member, dto);
        if (result > 0) {
            return Result.ok("用户信息更新成功！").put("data", dto);
        } else {
            log.error("用户信息更新失败！用户信息=>{}", memberVO);
            throw new ServiceException("用户信息更新失败");
        }
    }
}