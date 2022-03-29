package cn.goroute.smart.member.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.entity.dto.MemberDTO;
import cn.goroute.smart.common.entity.pojo.Member;
import cn.goroute.smart.common.entity.vo.MemberInfoUpdateVO;
import cn.goroute.smart.common.exception.BizCodeEnum;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.IllegalTextCheckUtil;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberService;
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

    /**
     *  更新用户展示信息
     * @param memberVO 用户展示信息VO
     * @return 更新结果
     */
    @Override
    public Result updateMemberInfo(MemberInfoUpdateVO memberVO) {

        //检查用户名是否存在违禁词
        Boolean nickNameCheckResult = illegalTextCheckUtil.checkText(memberVO.getNickName());
        Boolean introCheckResult = illegalTextCheckUtil.checkText(memberVO.getIntro());
        if (nickNameCheckResult || introCheckResult) {
            return Result.error(BizCodeEnum.ILLEGAL_TEXT.getCode(),BizCodeEnum.ILLEGAL_TEXT.getMessage());
        }
        Member member = new Member();
        String loginId = StpUtil.getLoginIdAsString();
        BeanUtils.copyProperties(memberVO, member);
        member.setUid(loginId);
        int result = memberDao.updateById(member);
        MemberDTO dto = new MemberDTO();
        BeanUtils.copyProperties(member,dto);
        if (result >0) {
            return Result.ok("用户信息更新成功！").put("data",dto);
        } else {
            log.error("用户信息更新失败！用户信息=>{}",memberVO);
            throw new ServiceException("用户信息更新失败");
        }
    }
}