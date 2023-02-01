package cn.goroute.smart.search.model.index;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/14:04
 * @Description: 文章es索引
 */
@Data
@Document(indexName = "smart_post")
@FieldNameConstants
public class PostIndex {

	/**
	 * 主键id
	 */
	private Long id;

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
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
	private String title;

	/**
	 * 文章内容
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
	private String content;

	/**
	 * 文章状态 0 = 正常
	 */
	private Integer state;

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
	 * 0:false = 不公布  1:true = 公布
	 */
	private Boolean isPublish;

	/**
	 * 文章作者发布时的ip
	 */
	private String ip;

	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 逻辑删除 0 = 未删除 1 = 已删除
	 */
	private Integer deleted;


}
