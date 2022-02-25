package cn.goroute.smart.post.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 板块表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@Data
@TableName("t_section")
public class SectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 板块uid
	 */
	@TableId
	private String uid;
	/**
	 * 板块名称
	 */
	private String name;
	/**
	 * 板块图标
	 */
	private String icon;
	/**
	 * 板块链接
	 */
	private String url;
	/**
	 * 板块介绍
	 */
	private String intro;
	/**
	 * 
	 */
	private Date updatedTime;
	/**
	 * 
	 */
	private Date createdTime;

}
