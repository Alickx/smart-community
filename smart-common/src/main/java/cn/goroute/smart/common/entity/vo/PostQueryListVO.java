package cn.goroute.smart.common.entity.vo;

import cn.goroute.smart.common.utils.QueryParam;
import lombok.Data;

@Data
public class PostQueryListVO extends QueryParam {

    private String curPage;

    private String limit;

    private String sidx;

    private String order;

    private String memberUid;

}
