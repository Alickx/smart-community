package cn.goroute.smart.member.entity.vo;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author Alickx
 */
@Data
public class MemberLoginVo {



    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$", message = "用户名格式不正确")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,18}$", message = "密码格式不正确")
    private String passWord;

}
