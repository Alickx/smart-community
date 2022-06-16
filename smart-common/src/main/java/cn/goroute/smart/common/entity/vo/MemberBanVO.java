package cn.goroute.smart.common.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/05/23/20:34
 * @Description: 封禁用户VO类
 */
@Data
@Accessors(chain = true)
public class MemberBanVO {

    /**
     * 封禁用户id
     */
    @NotNull
    private String memberUid;

    /**
     * 封禁原因
     */
    private String banReason;

    /**
     * 封禁时间，结束日期
     */
    @NotNull
    private String banTime;

    /**
     * 封禁类型
     */
    @NotNull
    private List<Integer> banType;


}
