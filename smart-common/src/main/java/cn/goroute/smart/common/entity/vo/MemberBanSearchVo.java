package cn.goroute.smart.common.entity.vo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: Alickx
 * @Date: 2022/05/31/10:16
 * @Description:
 */
@Data
@ToString
public class MemberBanSearchVo {

    /**
     * 当前页数
     */
    @NotEmpty
    private String curPage;

    /**
     * 每页显示条数
     */
    @NotEmpty
    private String pageSize;

    /**
     * 搜索内容
     */
    private String searchValue;

    /**
     * 搜索类型
     */
    private String searchType;

    /**
     * 封禁开始时间
     */
    private String startTime;

    /**
     * 封禁结束时间
     */
    private String endTime;


}
