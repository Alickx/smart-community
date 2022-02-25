package cn.goroute.smart.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 收藏表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
@Data
@TableName("t_collect")
public class CollectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键uid
	 */
	@TableId
	private String uid;
	/**
	 * 用户uid
	 */
	private String memberUid;
	/**
	 * 文章uid
	 */
	private String postUid;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createdTime;
	/**
	 * 
	 */
	private Date updatedTime;

}
