package cn.goroute.smart.post.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
	private String uid;
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
	private Date createdTime;

}
