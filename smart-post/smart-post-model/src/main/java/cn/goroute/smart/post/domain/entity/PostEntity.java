package cn.goroute.smart.post.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章表
 * @author Alickx
 * @TableName post
 */
@TableName(value ="post")
@Data
public class PostEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 板块名称
     */
    private String categoryName;

	/**
	 * 标签名称
	 */
	private String tagName;

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
	 * 文章摘要
	 */
	private String summary;

    /**
     * 文章状态 0 = 正常
     */
    private Integer state;

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
	@TableLogic
    private Integer deleted;

    @Serial
	@TableField(exist = false)
    private static final long serialVersionUID = 1L;
}