package cn.goroute.smart.post.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * 标签表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value ="t_tag")
public class Tag extends BaseEntity implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	/**
	 * 内容
	 */
	private String content;
	/**
	 * 介绍
	 */
	private String intro;
	/**
	 * 排序字段
	 */
	private Integer sort;


}
