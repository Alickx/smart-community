package cn.goroute.smart.member.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.entity.dto.MemberDTO;
import cn.goroute.smart.common.entity.pojo.MemberEntity;
import cn.goroute.smart.common.entity.vo.MemberInfoUpdateVO;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.IllegalTextCheckUtil;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service("memberService")
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

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
        List<String> nickNameCheckResult = illegalTextCheckUtil.checkText(memberVO.getNickName());
        List<String> introCheckResult = illegalTextCheckUtil.checkText(memberVO.getIntro());
        if (CollUtil.isNotEmpty(nickNameCheckResult) || CollUtil.isNotEmpty(introCheckResult)) {
            Collection<String> resultCollection = CollUtil.addAll(nickNameCheckResult, introCheckResult);
            return Result.error().put("data",resultCollection);
        }
        MemberEntity memberEntity = new MemberEntity();
        String loginId = StpUtil.getLoginIdAsString();
        BeanUtils.copyProperties(memberVO,memberEntity);
        memberEntity.setUid(loginId);
        int result = memberDao.updateById(memberEntity);
        MemberDTO dto = new MemberDTO();
        BeanUtils.copyProperties(memberEntity,dto);
        if (result >0) {
            return Result.ok("用户信息更新成功！").put("data",dto);
        } else {
            log.error("用户信息更新失败！用户信息=>{}",memberVO);
            throw new ServiceException("用户信息更新失败");
        }
    }
}