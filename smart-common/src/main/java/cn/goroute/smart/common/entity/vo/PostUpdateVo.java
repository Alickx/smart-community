package cn.goroute.smart.common.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/04/01/21:05
 * @Description: 帖子更新VO
 */
@Data
public class PostUpdateVo {

    /**
     * 文章uid
     */
    @NotNull(message = "文章uid不能为空")
    private Long uid;

    /**
     * 分类id
     */
    @NotNull(message = "分类id不能为空")
    private Long categoryUid;

    /**
     * 标签id
     */
    @NotBlank(message = "标签id不能为空")
    private List<Long> tagUid;
    /**
     *
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    /**
     * 0 = 不公布  1 = 公布
     */
    @NotNull(message = "是否公布不能为空")
    private Boolean isPublish;
    /**
     *
     */
    private String summary;
    /**
     * HTML标签
     */
    private String contentHtml;

}
