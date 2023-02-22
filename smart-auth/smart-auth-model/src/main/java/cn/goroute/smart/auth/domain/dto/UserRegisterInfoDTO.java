package cn.goroute.smart.auth.domain.dto;

import lombok.Data;

/**
 * @author: Alickx
 * @Date: 2023/02/21 16:01:06
 * @Description:
 */
@Data
public class UserRegisterInfoDTO {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 激活token
     */
    private String token;

}
