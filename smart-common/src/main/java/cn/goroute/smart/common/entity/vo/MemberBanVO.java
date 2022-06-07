package cn.goroute.smart.common.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author: Alickx
 * @Date: 2022/05/23/20:34
 * @Description: 封禁用户VO类
 */
@Data
@Accessors(chain = true)
public class MemberBanVO {

    @NotNull
    private String memberId;

    private String banReason;

    @NotNull
    private String banTime;

    @NotNull
    private String banEndTime;

    @NotNull
    private Integer banType;


}
