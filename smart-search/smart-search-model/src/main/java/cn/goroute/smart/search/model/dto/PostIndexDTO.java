package cn.goroute.smart.search.model.dto;

import cn.goroute.smart.user.domain.vo.UserProfileVO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Alickx
 * @Date: 2023/02/04 20:22:43
 * @Description: 搜索页面返回对象
 */
@Data
public class PostIndexDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 作者信息
     */
    private UserProfileVO author;

    /**
     * 文章题目
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

	/**
	 * 文章标签名字
	 */
	private String tagName;

	/**
	 * 文章板块id
	 */
	private String categoryName;


    /**
     * 收藏数量
     */
    private Integer collectCount;

    /**
     * 点赞数量
     */
    private Integer thumbCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
