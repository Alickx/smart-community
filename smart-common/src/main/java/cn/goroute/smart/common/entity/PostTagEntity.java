package cn.goroute.smart.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章标签表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@Data
@TableName("t_post_tag")
public class PostTagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private int uid;
	/**
	 * 
	 */
	private String postUid;
	/**
	 * 
	 */
	private String tagUid;
	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createdTime;

}
