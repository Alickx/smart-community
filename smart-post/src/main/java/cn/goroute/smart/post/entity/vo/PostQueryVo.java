package cn.goroute.smart.post.entity.vo;

import cn.goroute.smart.common.utils.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Alickx
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostQueryVo extends QueryParam {

    /**
     * 分类
     */
    private Long categoryUid;

    /**
     * 标签id
     */
    private Long tagUid;

}
