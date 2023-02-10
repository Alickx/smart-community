package cn.goroute.smart.post.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 点赞表
 * @TableName thumb
 */
@TableName(value ="thumb")
@Data
@ToString
public class Thumb implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 点赞用户id
     */
    private Long userId;

    /**
     * 点赞内容的id
     */
    private Long toId;

    /**
     * 点赞类型 0 = 评论 1 = 文章
     */
    private Integer type;

	/**
	 * 逻辑删除 1 = 已取消点赞
	 */
    @TableLogic
	private Integer deleted;

    /**
     * 点赞时间
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