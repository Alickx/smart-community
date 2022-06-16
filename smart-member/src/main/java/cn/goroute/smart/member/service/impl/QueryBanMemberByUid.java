package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.common.constant.Constant;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.dao.MemberBanDao;
import cn.goroute.smart.common.entity.dto.MemberBanDTO;
import cn.goroute.smart.common.entity.dto.MemberDTO;
import cn.goroute.smart.common.entity.pojo.Member;
import cn.goroute.smart.common.entity.pojo.MemberBan;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/05/31/10:35
 * @Description:
 */
@Service
public class QueryBanMemberByUid implements IQueryBanMember {
    
    @Autowired
    MemberDao memberDao;

    @Autowired
    MemberBanDao memberBanDao;


    @Override
    public PageUtils queryBanMember(MemberBanSearchVO memberBanSearchVO) {

        String memberId = memberBanSearchVO.getSearchValue();
        String curPage = memberBanSearchVO.getCurPage();
        String pageSize = memberBanSearchVO.getPageSize();

        IPage<MemberBan> page = new Query<MemberBan>().getPage(curPage, pageSize);

        IPage<MemberBan> userBanIPage = memberBanDao.selectPage(page, new LambdaQueryWrapper<MemberBan>().eq(MemberBan::getBanUserId, memberId));

        if (CollectionUtil.isEmpty(userBanIPage.getRecords())) {
            return new PageUtils(userBanIPage);
        }

        List<MemberBanDTO> result = new ArrayList<>();

        for (MemberBan memberBan : userBanIPage.getRecords()) {
            Member banMember = memberDao.selectById(memberBan.getBanUserId());
            Member HandlerMember = memberDao.selectById(memberBan.getBanHandlerId());
            MemberBanDTO memberBanDTO = new MemberBanDTO();
            memberBanDTO.setBanUser(ModelConverterUtils.convert(banMember, MemberDTO.class));
            memberBanDTO.setBanHandlerUser(ModelConverterUtils.convert(HandlerMember, MemberDTO.class));
            memberBanDTO.setBanReason(memberBan.getBanReason());
            memberBanDTO.setBanType(Constant.getBanType(String.valueOf(memberBan.getBanType())));
            memberBanDTO.setBanTime(memberBan.getBanTime());
            memberBanDTO.setBanEndTime(memberBan.getBanEndTime());
            result.add(memberBanDTO);
        }

        PageUtils pageUtils = new PageUtils(userBanIPage);
        pageUtils.setList(result);
        return pageUtils;
    }
}
