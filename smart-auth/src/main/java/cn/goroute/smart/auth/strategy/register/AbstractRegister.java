package cn.goroute.smart.auth.strategy.register;

import cn.goroute.smart.common.feign.FeignUserProfileService;
import cn.goroute.smart.common.entity.dto.UserProfileDTO;
import com.hccake.ballcat.common.model.result.R;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @Author: Alickx
 * @Date: 2022/09/18/18:02
 * @Description: 注册策略抽象类
 */
@Slf4j
@NoArgsConstructor
public abstract class AbstractRegister {

    @Resource
    protected  FeignUserProfileService feignUserProfileService;

    protected Boolean initUserProfile(UserProfileDTO userProfileDto){
        R<Boolean> resp = feignUserProfileService.initUserProfile(userProfileDto);
        return resp.getCode() == 200;
    }

}