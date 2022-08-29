package cn.goroute.smart.post.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 文章表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_post")
@ToString
public class Post extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类uid
	 */
	private Long categoryId;
	/**
	 * 用户uid
	 */
	private Long memberId;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章内容
	 */
	private String content;
	/**
	 * 文章状态 0 = 正常
	 */
	private Integer status;
	/**
	 * 收藏数量
	 */
	private Long collectCount;
	/**
	 * 点赞数量
	 */
	private Long thumbCount;
	/**
	 * 0 = 不公布  1 = 公布
	 */
	private String isPublish;
	/**
	 * 文章摘要
	 */
	private String summary;

	/**
	 * 评论总量
	 */
	private Long commentCount;

}
