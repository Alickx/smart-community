package cn.goroute.smart.user.domain.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Alickx
 * @Date: 2023/03/19/11:19
 * @Description:
 */
@Data
public class UserProfileUploadForm {

    /**
     * 呢称
     */
    @NotBlank(message = "呢称不能为空")
    private String nickName;

//    /**
//     * 头像地址
//     */
//    @NotBlank(message = "头像地址不能为空")
//    private String avatar;

    /**
     * 个人介绍
     */
    @NotBlank(message = "个人介绍不能为空")
    private String intro;

}
