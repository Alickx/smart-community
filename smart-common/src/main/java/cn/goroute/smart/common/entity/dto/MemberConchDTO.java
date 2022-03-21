package cn.goroute.smart.common.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Alickx
 * @Date: 2022/03/20/9:36
 * @Description: 用户贝壳余额DTO
 */
@Data
public class MemberConchDTO {

    /**
     * 用户贝壳余额
     */
    private BigDecimal conch;

}
