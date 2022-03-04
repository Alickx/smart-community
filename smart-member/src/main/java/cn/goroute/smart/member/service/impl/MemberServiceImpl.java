package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.entity.MemberEntity;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;
import cn.goroute.smart.member.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service("memberService")
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Resource
    MemberDao memberDao;

    private static final int BAN_TIME = 10 * 60;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }


}