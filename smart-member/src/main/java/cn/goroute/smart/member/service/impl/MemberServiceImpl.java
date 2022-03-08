package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.entity.MemberEntity;
import cn.goroute.smart.member.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service("memberService")
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {



}