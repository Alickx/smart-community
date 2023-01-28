package cn.goroute.smart.auth.model;

import cn.goroute.smart.common.model.dto.UserProfileDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springdoc.api.annotations.ParameterObject;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/16:05
 * @Description: 登录成功返回实体类
 */
@Data
@Accessors(chain = true)
@Schema(title = "用户信息类")
@ParameterObject
public class CustomUserDetails {

    /**
     * 通行token
     */
    @Parameter(description = "通行token")
    private String accessToken;

    /**
     * 用戶信息
     */
    @Parameter(description = "用戶信息")
    private UserProfileDTO userProfile;

}
