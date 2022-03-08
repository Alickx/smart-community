package cn.goroute.smart.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 点赞记录表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-26 16:17:45
 */
@Data
@TableName("t_thumb")
public class ThumbEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer uid;
	/**
	 * 点赞用户的id
	 */
	private String memberUid;
	/**
	 * 点赞对象的uid
	 */
	private String toUid;
	/**
	 * 点赞的类型 0 = 文章 1 = 评论
	 */
	private Integer thumbType;
	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createdTime;

}
