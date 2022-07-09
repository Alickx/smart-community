package cn.goroute.smart.member.service;

import cn.goroute.smart.common.entity.pojo.MemberBan;
import cn.goroute.smart.common.entity.vo.MemberBanSearchVo;
import cn.goroute.smart.common.entity.vo.MemberBanVo;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Alickx
* @description 针对表【t_user_ban(封禁表)】的数据库操作Service
* @createDate 2022-05-23 20:44:28
*/
public interface MemberBanService extends IService<MemberBan> {

    /**
     * @param memberBanVO 封禁用户VO类
     * @return 结果类
     * @Description: 封禁用户
     */
    Result banUser(MemberBanVo memberBanVO);

    Result batchQueryUsers(MemberBanSearchVo memberBanSearchVO);

    Result removeBannedUsers(List<String> banIds);

    Result queryBannedMember(Long memberUid);
}
