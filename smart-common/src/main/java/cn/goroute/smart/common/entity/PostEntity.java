package cn.goroute.smart.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@Data
@TableName("t_post")
public class PostEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@TableField(fill = FieldFill.INSERT)
	private String uid;
	/**
	 * 
	 */
	private String sectionUid;
	/**
	 * 
	 */
	private String memberUid;
	/**
	 * 
	 */
	@NotBlank
	private String title;
	/**
	 * 
	 */
	private String content;
	/**
	 * 文章状态 0 = 正常
	 */
	private Integer status;
	/**
	 * 
	 */
	private String headImg;
	/**
	 * 
	 */
	private Integer collectCount;
	/**
	 * 
	 */
	private Integer thumbCount;
	/**
	 * 0 = 不公布  1 = 公布
	 */
	private String isPublish;
	/**
	 * 
	 */
	private String summary;
	/**
	 * 
	 */
	private Integer clickCount;
	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updatedTime;
	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createdTime;

}
