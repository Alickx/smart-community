package cn.goroute.smart.member.service;

import cn.goroute.smart.common.entity.vo.MemberBanSearchVo;
import cn.goroute.smart.common.utils.PageUtils;

/**
 * @Author: Alickx
 * @Date: 2022/05/31/10:27
 * @Description:
 */
public interface IQueryBanMember {

    /**
     * 查询封禁的用户
     * @return List<MemberDTO>
     */
    PageUtils queryBanMember(MemberBanSearchVo memberBanSearchVO);

}
