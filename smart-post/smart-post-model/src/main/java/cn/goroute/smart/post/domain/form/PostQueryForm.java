package cn.goroute.smart.post.domain.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Alickx
 * @Date: 2023/03/26/23:11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostQueryForm {

    /**
     * 文章id
     */
    private String postId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 作者id
     */
    private Long userId;

}
