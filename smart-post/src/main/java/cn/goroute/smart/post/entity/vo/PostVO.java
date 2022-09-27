package cn.goroute.smart.post.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/10:22
 * @Description: 文章
 */
@Data
@ToString
public class PostVO {

	/**
	 * 板块id
	 */
	private Long categoryId;

	/**
	 * 标签id
	 */
	private Long tagId;

	/**
	 * 作者id
	 */
	private Long authorId;

	/**
	 * 文章题目
	 */
	private String title;

	/**
	 * 文章内容
	 */
	private String content;

	/**
	 * 0 = 不公布  1 = 公布
	 */
	private Integer isPublish;

	/**
	 * 文章摘要
	 */
	private String summary;

}
