package cn.goroute.smart.post.entity.vo;

import cn.goroute.smart.base.entity.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Alickx
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostQueryVo extends BasePage {

    /**
     * 分类
     */
    private Long categoryId;

    /**
     * 标签id
     */
    private Long tagId;

}
