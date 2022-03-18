package cn.goroute.smart.common.utils;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QueryParam {

    /**
     * 页数
     */
    @NotNull
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

}
