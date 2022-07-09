package cn.goroute.smart.common.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alickx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostVo {

    /**
     * 文章主键
     */
    private Long uid;

    /**
     * 分类id
     */
    @NotNull(message = "分类id不能为空")
    private Long categoryUid;

    /**
     * 标签id
     */
    @NotNull(message = "标签id不能为空")
    private List<Long> tagUid;
    /**
     *
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    /**
     *
     */
    @NotBlank(message = "内容不能为空")
    private String content;
    /**
     * 0 = 不公布  1 = 公布
     */
    @NotNull(message = "公布状态不能为空")
    private Boolean isPublish;
    /**
     *
     */
    private String summary;
    /**
     * HTML标签
     */
    @NotBlank(message = "文章内容错误")
    private String contentHtml;

    /**
     * 类型
     */
    private String type;
}
