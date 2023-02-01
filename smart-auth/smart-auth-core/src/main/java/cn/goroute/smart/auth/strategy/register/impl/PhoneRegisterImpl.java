package cn.goroute.smart.auth.strategy.register.impl;

import cn.goroute.smart.auth.model.vo.UserRegisterVO;
import cn.goroute.smart.auth.strategy.register.IRegister;
import cn.goroute.smart.user.model.dto.UserProfileDTO;
import com.hccake.ballcat.common.model.result.R;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/17:46
 * @Description: 手机号注册实现
 */
public class PhoneRegisterImpl implements IRegister {
    /**
     * 注册
     *
     * @param userRegisterVo 注册信息
     * @return R 注册的结果
     */
    @Override
    public R<Boolean> register(UserRegisterVO userRegisterVo) {
        return null;
    }

    /**
     * 发布事件
     */
    @Override
    public void publishEvent(UserProfileDTO userProfileDto) {

    }
}
