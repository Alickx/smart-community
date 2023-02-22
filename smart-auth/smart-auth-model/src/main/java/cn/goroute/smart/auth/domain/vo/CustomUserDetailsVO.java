package cn.goroute.smart.auth.domain.vo;


import cn.goroute.smart.user.domain.vo.UserProfileVO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/16:05
 * @Description: 登录成功返回实体类
 */
@Data
@Accessors(chain = true)
public class CustomUserDetailsVO {

    /**
     * 通行token
     */
    private String accessToken;

    /**
     * 用戶信息
     */
    private UserProfileVO userProfile;

}
