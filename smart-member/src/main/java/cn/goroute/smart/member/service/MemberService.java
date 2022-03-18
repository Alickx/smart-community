package cn.goroute.smart.member.service;

import cn.goroute.smart.common.entity.pojo.MemberEntity;
import cn.goroute.smart.common.entity.vo.MemberInfoUpdateVO;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户信息表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
public interface MemberService extends IService<MemberEntity> {

    Result updateMemberInfo(MemberInfoUpdateVO memberVO);


}


