package cn.goroute.smart.member.service;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.member.entity.pojo.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Alickx
* @description 针对表【t_follow(用户关注表)】的数据库操作Service
* @createDate 2022-03-27 16:58:13
*/
public interface FollowService extends IService<Follow> {

    /**
     * 关注用户
     * @param followMemberId 关注目标的uid
     * @return 关注结果
     */
    Response saveFollow(Long followMemberId);

    Response queryFollow(Long followMemberId);
}
