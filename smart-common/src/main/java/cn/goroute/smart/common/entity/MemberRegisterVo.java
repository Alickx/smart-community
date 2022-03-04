package cn.goroute.smart.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterVo {

    private String username;

    private String passWord;

    private String captcha;

    private Boolean rememberMe;

}
