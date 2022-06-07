package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.common.constant.Constant;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.dao.UserBanDao;
import cn.goroute.smart.common.entity.dto.MemberBanDTO;
import cn.goroute.smart.common.entity.dto.MemberDTO;
import cn.goroute.smart.common.entity.pojo.Member;
import cn.goroute.smart.common.entity.pojo.UserBan;
import cn.goroute.smart.common.entity.vo.MemberBanSearchVO;
import cn.goroute.smart.common.utils.ModelConverterUtils;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;
import cn.goroute.smart.member.service.IQueryBanMember;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/05/31/19:23
 * @Description:
 */
@Service
public class QueryBanMember implements IQueryBanMember {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private UserBanDao userBanDao;

    @Override
    public PageUtils queryBanMember(MemberBanSearchVO memberBanSearchVO) {

        String pageSize = memberBanSearchVO.getPageSize();
        String curPage = memberBanSearchVO.getCurPage();

        IPage<UserBan> userBanPage;

        if (memberBanSearchVO.getStartTime() != null) {
            userBanPage = userBanDao.selectPage(new Query<UserBan>().getPage(curPage, pageSize), new LambdaQueryWrapper<UserBan>()
                    .ge(UserBan::getBanTime, LocalDateTime.ofEpochSecond(Long.parseLong(memberBanSearchVO.getStartTime())/ 1000, 0, ZoneOffset.ofHours(8)))
                    .le(UserBan::getBanTime, LocalDateTime.ofEpochSecond(Long.parseLong(memberBanSearchVO.getEndTime()) / 1000, 0, ZoneOffset.ofHours(8))));
        } else {
            userBanPage = userBanDao.selectPage(new Query<UserBan>().getPage(curPage, pageSize), new LambdaQueryWrapper<>());
        }
        if (CollectionUtil.isEmpty(userBanPage.getRecords())) {
            return new PageUtils(userBanPage);
        }

        List<MemberBanDTO> result = new ArrayList<>();
        userBanPage.getRecords().forEach(userBan -> {
            MemberBanDTO memberBanDTO = new MemberBanDTO();
            Member banMember = memberDao.selectById(userBan.getBanUserId());
            Member handlerMember = memberDao.selectById(userBan.getBanHandlerId());
            memberBanDTO.setBanId(userBan.getUid());
            memberBanDTO.setBanUser(ModelConverterUtils.convert(banMember, MemberDTO.class));
            memberBanDTO.setBanHandlerUser(ModelConverterUtils.convert(handlerMember, MemberDTO.class));
            memberBanDTO.setBanTime(userBan.getBanTime());
            memberBanDTO.setBanType(Constant.getBanType(String.valueOf(userBan.getBanType())));
            memberBanDTO.setBanReason(userBan.getBanReason());
            memberBanDTO.setBanEndTime(userBan.getBanEndTime());
            result.add(memberBanDTO);
        });

        PageUtils pageUtils = new PageUtils(userBanPage);
        pageUtils.setList(result);
        return pageUtils;
    }
}
