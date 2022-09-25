package cn.goroute.smart.auth.strategy.register.impl;

import cn.goroute.smart.auth.domain.AuthUser;
import cn.goroute.smart.auth.entity.vo.UserRegisterVO;
import cn.goroute.smart.auth.mapper.AuthUserMapper;
import cn.goroute.smart.auth.strategy.register.AbstractRegister;
import cn.goroute.smart.auth.strategy.register.IRegister;
import cn.goroute.smart.auth.strategy.register.RegisterEnum;
import cn.goroute.smart.common.constant.ErrorCodeEnum;
import cn.goroute.smart.common.entity.dto.UserProfileDto;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/17:12
 * @Description: 邮箱注册策略
 */
@Slf4j
@Component(value = "EmailRegister")
@RequiredArgsConstructor
public class EmailRegisterImpl extends AbstractRegister implements IRegister {

    private final AuthUserMapper authUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> register(UserRegisterVO userRegisterVo) {

        AuthUser authUser = new AuthUser();
        authUser.setIdentityType(RegisterEnum.Email.getRegisterType());
        authUser.setIdentifier(userRegisterVo.getUsername());
        authUser.setCertificate(encryptPassword(userRegisterVo.getPassword()));
        authUserMapper.insert(authUser);

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUserId(authUser.getId());
        Boolean initResult = super.initUserProfile(userProfileDto);
        // TODO 分布式事务
        if (!initResult) {
            log.error("初始化用户信息失败,用户信息:{}", authUser);
            return R.failed(ErrorCodeEnum.SYSTEM_ERROR);
        }
        this.publishEvent(userProfileDto);

        return R.ok(true);

    }

    /**
     * 发布事件
     */
    @Override
    public void publishEvent(UserProfileDto userProfileDto) {

    }
}
