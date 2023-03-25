package cn.goroute.smart.search.model.index;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
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
	@Id
	private Long id;

	/**
	 * 板块id
	 */
	@Field(type = FieldType.Keyword)
	private String categoryName;

	/**
	 * 作者id
	 */
	@Field(type = FieldType.Long)
	private Long authorId;


	/**
	 * 文章标签名字
	 */
	@Field(type = FieldType.Keyword)
	private String tagName;

	/**
	 * 文章题目
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", copyTo = "descriptiveContent")
	private String title;

	/**
	 * 文章内容
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", copyTo = "descriptiveContent")
	private String content;

	/**
	 * 文章摘要
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String summary;

	/**
	 * 文章状态 0 = 正常
	 */
	@Field(type = FieldType.Integer)
	private Integer state;

	/**
	 * 0:false = 不公布  1:true = 公布
	 */
	@Field(type = FieldType.Boolean)
	private Boolean isPublish;

	/**
	 * 文章作者发布时的ip
	 */
	@Field(type = FieldType.Keyword)
	private String ip;

	/**
	 * 更新时间
	 */
	@Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;

	/**
	 * 创建时间
	 */
	@Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd HH:mm:ss")
	private LocalDateTime createTime;

	/**
	 * 逻辑删除 0 = 未删除 1 = 已删除
	 */
	@Field(type = FieldType.Integer)
	private Integer deleted;

	/**
	 * 主要作用于搜索功能，无存储作用
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", ignoreFields = "descriptiveContent", excludeFromSource = true)
	@JSONField(serialize = false)
	private String descriptiveContent;


}
