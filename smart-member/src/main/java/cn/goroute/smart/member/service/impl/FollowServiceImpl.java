package cn.goroute.smart.member.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.member.mapper.FollowMapper;
import cn.goroute.smart.member.mapper.MemberMapper;
import cn.goroute.smart.member.entity.pojo.Follow;
import cn.goroute.smart.member.entity.pojo.Member;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.FollowService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Alickx
 * @description 针对表【t_follow(用户关注表)】的数据库操作Service实现
 * @createDate 2022-03-27 16:58:13
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow>
        implements FollowService {

    @Resource
    FollowMapper followMapper;

    @Resource
    MemberMapper memberMapper;

    @Autowired
    AuthService authService;

    @Override
    public Result saveFollow(Long followMemberId) {

        Member member = memberMapper.selectById(followMemberId);

        if (member == null) {
            return Result.error("该用户不存在!");
        }

        Follow follow = new Follow();
        follow.setToMemberUid(followMemberId);
        follow.setMemberUid(authService.getLoginUid());

        int insert = followMapper.insert(follow);

        if (insert == 0) {
            return Result.error();
        }
        return Result.ok();

    }

    /**
     * 查询是否关注
     *
     * @param followMemberId 被关注人的id
     * @return 是否关注
     */
    @Override
    public Result queryFollow(Long followMemberId) {

        if (!StpUtil.isLogin()) {
            return Result.ok().put("data",false);
        }

        Follow follow = followMapper.selectOne(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getMemberUid, StpUtil.getLoginIdAsString())
                .eq(Follow::getToMemberUid, followMemberId));

        if (follow == null) {
            return Result.ok().put("data",false);
        }
        return Result.ok().put("data", true);
    }
}




