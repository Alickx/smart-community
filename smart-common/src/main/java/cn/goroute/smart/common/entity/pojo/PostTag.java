package cn.goroute.smart.common.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 文章标签表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_post_tag")
public class PostTag extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long postUid;
	/**
	 * 
	 */
	private int tagUid;

}
