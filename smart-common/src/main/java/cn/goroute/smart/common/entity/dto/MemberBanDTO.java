package cn.goroute.smart.common.entity.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2022/05/31/19:53
 * @Description:
 */
@Data
@ToString
public class MemberBanDTO {

    /**
     * 封禁表主键
     */
    private Long banId;

    /**
     * 封禁用户
     */
    private MemberDTO banUser;

    /**
     * 操作封禁者
     */
    private MemberDTO banHandlerUser;

    /**
     * 封禁原因
     */
    private String banReason;

    /**
     * 封禁类型
     */
    private String banType;

    /**
     * 封禁时间
     */
    private LocalDateTime banTime;

    /**
     * 封禁结束时间
     */
    private LocalDateTime banEndTime;

}
