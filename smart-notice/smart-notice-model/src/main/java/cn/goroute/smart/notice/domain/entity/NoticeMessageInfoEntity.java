package cn.goroute.smart.notice.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知消息详细表
 * @TableName t_notice_message_info
 */
@TableName(value ="t_notice_message_info")
@Data
public class NoticeMessageInfoEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 对象id 例如文章,评论等
     */
    private Long targetId;

	/**
	 * 文章id
	 */
	private Long postId;

	/**
	 * 文章标题
	 */
	private String postTitle;

    /**
     * 通知消息内容
     */
    private String content;

    /**
     * 通知消息类型 0 = 点赞 1 = 评论 2 = 关注 3 = @操作 4 = 系统提醒
     */
    private Integer msgType;

	/**
	 * 源类型 0 = 文章 1 = 评论
	 */
	private Integer sourceType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @Serial
	@TableField(exist = false)
    private static final long serialVersionUID = 1L;
}