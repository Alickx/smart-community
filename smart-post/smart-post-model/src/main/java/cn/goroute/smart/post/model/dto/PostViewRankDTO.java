package cn.goroute.smart.post.model.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

/**
 * @author: Alickx
 * @Date: 2023/02/09 15:55:19
 * @Description:
 */
@Data
@Schema(name = "文章浏览量排行榜返回对象")
@ParameterObject
public class PostViewRankDTO {

    @Parameter(description = "文章id")
    private Long postId;

    @Parameter(description = "文章标题")
    private String title;

}
