package cn.goroute.smart.auth.strategy.register;

import cn.goroute.smart.auth.entity.vo.UserRegisterVo;
import cn.goroute.smart.common.entity.dto.UserProfileDto;
import cn.hutool.crypto.digest.BCrypt;
import com.hccake.ballcat.common.model.result.R;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/17:10
 * @Description: 注册接口
 */
public interface IRegister {

    /**
     * 注册
     *
     * @param userRegisterVo 注册信息
     * @return R 注册的结果
     */
    R<Boolean> register(UserRegisterVo userRegisterVo);

    /**
     * 发布事件
     * @param userProfileDto 用户信息
     */
    void publishEvent(UserProfileDto userProfileDto);

    /**
     * 加密密码
     * @param password 密码
     * @return 加密后的密码
     */
    default String encryptPassword(String password) {
        return BCrypt.hashpw(password);
    }

}
