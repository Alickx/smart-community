package cn.goroute.smart.common.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询参数
 *
 * @author Hccake 2021/1/18
 * @version 1.0
 */
@Data
public class PageParam {


    @Min(value = 1, message = "当前页不能小于 1")
    private long page = 1;

    @Min(value = 1, message = "每页显示条数不能小于1")
    private long size = 10;

    @Valid
    private List<Sort> sorts = new ArrayList<>();

    @Getter
    @Setter
    public static class Sort {

        @Pattern(regexp = PageableConstants.SORT_FILED_REGEX, message = "排序字段格式非法")
        private String field;

        private boolean asc;

    }

}
