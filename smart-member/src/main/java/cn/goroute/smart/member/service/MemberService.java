package cn.goroute.smart.member.service;

import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.entity.pojo.Member;
import cn.goroute.smart.member.entity.vo.MemberInfoUpdateVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户信息表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
public interface MemberService extends IService<Member> {

    /**
     * 更新用户信息
     * @param memberVO 用户信息
     * @return 更新结果
     */
    Result updateMemberInfo(MemberInfoUpdateVo memberVO);

}


