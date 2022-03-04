package cn.goroute.smart.gateway.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.entity.MemberDTO;
import cn.goroute.smart.common.entity.MemberEntity;
import cn.goroute.smart.common.entity.MemberLoginVo;
import cn.goroute.smart.common.utils.R;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginApi {

    @Autowired
    MemberDao memberDao;

    @PostMapping
    public R login(@RequestBody MemberLoginVo memberLoginVo) {

        String username = memberLoginVo.getUsername();

        MemberEntity memberEntity = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("email", username));


        if (memberEntity == null) {
            return R.error("用户名不存在");
        }

        if (!DigestUtil.bcryptCheck(memberLoginVo.getPassWord(), memberEntity.getPassWord())) {
            return R.error("用户名或密码错误");
        }


        MemberDTO memberDTO = new MemberDTO();
        BeanUtils.copyProperties(memberEntity,memberDTO);

        StpUtil.login(memberEntity.getUid());

        return R.ok()
                .put("access_token", StpUtil.getTokenValue())
                .put("user_info",memberDTO)
                .put("permission_list",StpUtil.getPermissionList())
                .put("role_list", StpUtil.getRoleList());
    }

}
