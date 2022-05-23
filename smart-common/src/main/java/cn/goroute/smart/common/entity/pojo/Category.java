package cn.goroute.smart.common.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 板块表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_category")
public class Category extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 板块名称
	 */
	@NotBlank(message = "板块名称不能为空")
	private String name;
	/**
	 * 板块图标
	 */
	private String icon;
	/**
	 * 板块介绍
	 */
	private String intro;

}
