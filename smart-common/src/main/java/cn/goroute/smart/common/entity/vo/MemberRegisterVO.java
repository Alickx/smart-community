package cn.goroute.smart.common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterVO {

    private String username;

    private String passWord;

    private String captcha;

    private Boolean rememberMe;

}
