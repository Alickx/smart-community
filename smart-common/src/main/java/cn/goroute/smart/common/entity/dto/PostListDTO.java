package cn.goroute.smart.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Alickx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uid;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 收藏次数
     */
    private Integer collectCount;
    /**
     * 点赞次数
     */
    private Integer thumbCount;
    /**
     * 评论次数
     */
    private Integer commentCount;
    /**
     * 作者信息
     */
    private MemberDTO authorInfo;
    /**
     * 是否点赞
     */
    private Boolean isLike;
    /**
     * 是否收藏
     */
    private Boolean isCollect;

}
