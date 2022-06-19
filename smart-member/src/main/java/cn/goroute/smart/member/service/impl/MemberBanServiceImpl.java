package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.common.constant.Constant;
import cn.goroute.smart.common.dao.MemberBanDao;
import cn.goroute.smart.common.entity.pojo.MemberBan;
import cn.goroute.smart.common.entity.vo.MemberBanSearchVO;
import cn.goroute.smart.common.entity.vo.MemberBanVO;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.manage.IMemberBanManage;
import cn.goroute.smart.member.service.MemberBanService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alickx
 * @description 针对表【t_user_ban(封禁表)】的数据库操作Service实现
 * @createDate 2022-05-23 20:44:28
 */
@Service
@Slf4j
public class MemberBanServiceImpl extends ServiceImpl<MemberBanDao, MemberBan>
        implements MemberBanService {

    @Resource
    private MemberBanDao memberBanDao;

    @Autowired
    QueryBanMember queryBanMember;

    @Autowired
    QueryBanMemberByMemberName queryBanMemberByMemberName;

    @Autowired
    QueryBanMemberByUid queryBanMemberByUid;

    @Autowired
    AuthService authService;

    @Autowired
    IMemberBanManage memberBanManage;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result banUser(MemberBanVO memberBanVO) {

        // 检查是否已经被封禁
        List<Integer> banType = memberBanVO.getBanType();

        for (Integer type : banType) {
            MemberBan memberBan = memberBanDao
                    .selectOne(new LambdaQueryWrapper<MemberBan>()
                            .eq(MemberBan::getBanUserId, memberBanVO.getMemberUid())
                            .eq(MemberBan::getBanType, type)
                            .eq(MemberBan::getBanEndTime, LocalDateTime.now()));
            if (memberBan != null) {
                continue;
            }
            memberBan = new MemberBan();
            memberBan.setBanUserId(memberBanVO.getMemberUid());
            memberBan.setBanType(type);
            memberBan.setBanReason(memberBanVO.getBanReason());
            memberBan.setBanTime(LocalDateTimeUtil.now());
            memberBan.setBanHandlerId(authService.getLoginUid());
            memberBan.setBanEndTime(LocalDateTime.now().plusDays(Long.parseLong(memberBanVO.getBanTime())));
            memberBanDao.insert(memberBan);
            // 消息队列发送通知
            memberBanManage.sendNotify(memberBan, "您的账号已被封禁，原因是" + memberBan.getBanReason() + "，详情请查看个人中心");
        }

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

        PageUtils pageUtils;

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
            MemberBan memberBan = memberBanDao.selectById(banId);
            if (memberBan == null) {
                return Result.error("该用户未被封禁");
            }
            // 逻辑删除用户封禁记录
            memberBanDao.deleteById(banId);
            // 发送事件通知
            memberBanManage.sendNotify(memberBan,"您的封禁已被解除，详情请查看个人中心!");

        }
        // 日志记录
        log.info("解除用户封禁成功,banIds={}", banIds);
        return Result.ok("操作成功");
    }

    /**
     * 查询用户封禁项
     * @param memberUid 用户ID
     * @return 用户封禁项
     */
    @Override
    public Result queryBannedMember(Long memberUid) {

        // 查询用户封禁项
        List<MemberBan> memberBans = memberBanDao.selectList(new LambdaQueryWrapper<MemberBan>()
                .eq(MemberBan::getBanUserId, memberUid)
                .ge(MemberBan::getBanEndTime, LocalDateTime.now()));

        if (CollUtil.isEmpty(memberBans)) {
            return Result.ok().put("data", Collections.emptyList());
        }

        List<Integer> banType = memberBans.stream().map(MemberBan::getBanType).collect(Collectors.toList());

        return Result.ok().put("data", banType);
    }
}




