package cn.goroute.smart.auth.domain.dto;

import lombok.Data;

/**
 * @author: Alickx
 * @Date: 2023/02/19 17:07:35
 * @Description:
 */
@Data
public class AuthUserDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String userEmail;


}
