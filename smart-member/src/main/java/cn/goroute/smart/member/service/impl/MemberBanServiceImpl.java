package cn.goroute.smart.member.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.Constant;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.dao.UserBanDao;
import cn.goroute.smart.common.entity.pojo.UserBan;
import cn.goroute.smart.common.entity.vo.MemberBanSearchVO;
import cn.goroute.smart.common.entity.vo.MemberBanVO;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberBanService;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Alickx
* @description 针对表【t_user_ban(封禁表)】的数据库操作Service实现
* @createDate 2022-05-23 20:44:28
*/
@Service
@Slf4j
public class MemberBanServiceImpl extends ServiceImpl<UserBanDao, UserBan>
    implements MemberBanService {

    @Resource
    private UserBanDao userBanDao;

    @Resource
    private MemberDao memberDao;

    @Autowired
    QueryBanMember queryBanMember;

    @Autowired
    QueryBanMemberByMemberName queryBanMemberByMemberName;

    @Autowired
    QueryBanMemberByUid queryBanMemberByUid;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result banUser(MemberBanVO memberBanVO) {

        // 检查是否已经被封禁
        UserBan userBan = userBanDao.checkUserBan(memberBanVO.getMemberId(), memberBanVO.getBanType());

        if (userBan != null) {
            return Result.error("该用户已被封禁");
        }
        userBan = new UserBan();
        // 封禁用户
        userBan.setBanType(memberBanVO.getBanType());
        userBan.setBanReason(memberBanVO.getBanReason());
        userBan.setBanTime(LocalDateTimeUtil.parse(memberBanVO.getBanTime()));
        userBan.setBanEndTime(LocalDateTimeUtil.parse(memberBanVO.getBanEndTime()));
        userBan.setBanUserId(memberBanVO.getMemberId());
        userBan.setBanHandlerId(StpUtil.getLoginIdAsString());
        userBanDao.insert(userBan);
        // 返回结果
        return Result.ok("封禁成功");
    }


    /**
     * 分页查询用户
     *
     * @param memberBanSearchVO 封禁查询VO类
     * @return
     */
    @Override
    public Result batchQueryUsers(MemberBanSearchVO memberBanSearchVO) {

        // 查询类型为空则查询全部

        PageUtils pageUtils = null;

        if (StrUtil.isEmpty(memberBanSearchVO.getSearchType())) {
            pageUtils = queryBanMember.queryBanMember(memberBanSearchVO);
            return Result.ok("查询成功").put("data", pageUtils);
        }
        if (memberBanSearchVO.getSearchType().equals(Constant.BY_USER_NAME)) {
            // 根据用户名查询
            pageUtils = queryBanMemberByMemberName.queryBanMember(memberBanSearchVO);
            return Result.ok("查询成功").put("data", pageUtils);
        }
        if (memberBanSearchVO.getSearchType().equals(Constant.BY_USER_ID)) {
            // 根据用户ID查询
            pageUtils = queryBanMemberByUid.queryBanMember(memberBanSearchVO);
            return Result.ok("查询成功").put("data", pageUtils);
        }

        return Result.error("查询失败");

    }

    @Override
    public Result removeBannedUsers(List<String> banIds) {

        for (String banId : banIds) {
            // 根据banId查询用户
            UserBan userBan = userBanDao.selectById(banId);
            if (userBan == null) {
                return Result.error("该用户未被封禁");
            }
            // 逻辑删除用户封禁记录
            userBanDao.deleteById(banId);
        }
        // 日志记录
        log.info("解除用户封禁成功,banIds={}", banIds);
        return Result.ok("操作成功");
    }
}




