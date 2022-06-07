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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: Alickx
 * @Date: 2022/05/31/10:44
 * @Description:
 */
@Service
public class QueryBanMemberByMemberName implements IQueryBanMember {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private UserBanDao userBanDao;

    @Override
    public PageUtils queryBanMember(MemberBanSearchVO memberBanSearchVO) {

        String nickName = memberBanSearchVO.getSearchValue();

        String curPage = memberBanSearchVO.getCurPage();
        String pageSize = memberBanSearchVO.getPageSize();


        // 搜索状态为空或者为未封禁，则查询所有
        IPage<Member> memberPage = new Query<Member>().getPage(curPage, pageSize);

        IPage<Member> memberIPage = memberDao.selectPage(memberPage, new LambdaQueryWrapper<Member>()
                .like(Member::getNickName, nickName)
                .eq(Member::getStatus, Constant.BAD_USER_STATUS));

        List<Member> records = memberIPage.getRecords();

        List<Long> userIds = records.stream().map(Member::getUid).filter(Objects::nonNull).collect(Collectors.toList());

        List<MemberBanDTO> result = new ArrayList<>();

        userIds.parallelStream().forEach(userId -> {
            List<UserBan> userBans = userBanDao.selectList(new LambdaQueryWrapper<UserBan>()
                    .eq(UserBan::getBanUserId, userId));

            if (CollectionUtils.isEmpty(userBans)) {
                return;
            }

            userBans.forEach(userBan -> {
                // 如果不为空，则查询封禁者的DTO和操作者的DTO
                // 在records中查询封禁者的DTO
                Member banMember = records.stream().filter(member1 -> Objects.equals(member1.getUid(), userId)).findFirst().orElse(null);
                Member handlerMember = memberDao.selectById(userBans.get(0).getBanHandlerId());

                MemberBanDTO memberBanDTO = new MemberBanDTO();
                memberBanDTO.setBanUser(ModelConverterUtils.convert(banMember, MemberDTO.class));
                memberBanDTO.setBanHandlerUser(ModelConverterUtils.convert(handlerMember, MemberDTO.class));
                memberBanDTO.setBanReason(userBan.getBanReason());
                memberBanDTO.setBanTime(userBan.getBanTime());
                memberBanDTO.setBanType(Constant.getBanType(String.valueOf(userBan.getBanType())));
                memberBanDTO.setBanEndTime(userBan.getBanEndTime());
                result.add(memberBanDTO);
            });
        });
        PageUtils pageUtils = new PageUtils(memberIPage);
        pageUtils.setList(result);

        return pageUtils;
    }
}
