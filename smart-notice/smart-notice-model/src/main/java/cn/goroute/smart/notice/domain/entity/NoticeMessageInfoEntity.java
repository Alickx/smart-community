package cn.goroute.smart.notice.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知消息详细表
 * @TableName t_notice_message_info
 */
@TableName(value ="t_notice_message_info")
@Data
@Schema(description = "通知消息详细表")
@ParameterObject
public class NoticeMessageInfoEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
	@Parameter(description = "主键id")
    private Long id;

    /**
     * 对象id 例如文章,评论等
     */
	@Parameter(description = "对象id 例如文章,评论等")
    private Long targetId;

	/**
	 * 文章id
	 */
	@Parameter(description = "文章id")
	private Long postId;

	/**
	 * 文章标题
	 */
	@Parameter(description = "文章标题")
	private String postTitle;

    /**
     * 通知消息内容
     */
	@Parameter(description = "通知消息内容")
    private String content;

    /**
     * 通知消息类型 0 = 点赞 1 = 评论 2 = 关注 3 = @操作 4 = 系统提醒
     */
	@Parameter(description = "通知消息类型 0 = 点赞 1 = 评论 2 = 关注 3 = @操作 4 = 系统提醒")
    private Integer msgType;

	/**
	 * 源类型 0 = 文章 1 = 评论
	 */
	@Parameter(description = "源类型 0 = 文章 1 = 评论")
	private Integer sourceType;

    /**
     * 创建时间
     */
	@Parameter(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
	@Parameter(description = "更新时间")
    private LocalDateTime updateTime;

    @Serial
	@TableField(exist = false)
    private static final long serialVersionUID = 1L;
}