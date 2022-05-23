package cn.goroute.smart.common.utils;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Alickx
 */
@Data
public class QueryParam {

    /**
     * 页数
     */
    @NotBlank
    private String curPage;

    /**
     * 条数
     */
    private String limit;

    /**
     * 需要排序的字段
     */
    private String sidx;

    /**
     * asc：正序排序（默认）
     * desc：反序排序
     */
    private String order;

    /**
     * 查询类型
     */
    private Integer type;
    /**
     *
     */
    private Long uid;

}
