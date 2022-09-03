package cn.goroute.smart.member.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.member.mapper.FollowMapper;
import cn.goroute.smart.member.mapper.MemberMapper;
import cn.goroute.smart.member.entity.pojo.Follow;
import cn.goroute.smart.member.entity.pojo.Member;
import cn.goroute.smart.common.service.AuthService;
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
    public Response saveFollow(Long followMemberId) {

        Member member = memberMapper.selectById(followMemberId);

        if (member == null) {
            return Response.failure("该用户不存在!");
        }

        Follow follow = new Follow();
        follow.setToMemberId(followMemberId);
        follow.setMemberId(authService.getLoginUid());

        int insert = followMapper.insert(follow);

        if (insert == 0) {
            return Response.error();
        }
        return Response.success();

    }

    /**
     * 查询是否关注
     *
     * @param followMemberId 被关注人的id
     * @return 是否关注
     */
    @Override
    public Response queryFollow(Long followMemberId) {

        if (!StpUtil.isLogin()) {
            return Response.success(false);
        }

        Follow follow = followMapper.selectOne(new LambdaQueryWrapper<Follow>()
                .eq(Follow::getMemberId, StpUtil.getLoginIdAsString())
                .eq(Follow::getToMemberId, followMemberId));

        if (follow == null) {
            return Response.success(false);
        }
        return Response.success(true);
    }
}




