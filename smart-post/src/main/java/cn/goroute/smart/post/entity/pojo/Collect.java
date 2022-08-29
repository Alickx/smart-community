package cn.goroute.smart.post.entity.pojo;

import cn.goroute.smart.common.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 收藏表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_collect")
public class Collect extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户uid
	 */
	@NotNull(message = "用户id不能为空")
	private Long memberId;
	/**
	 * 文章uid
	 */
	@NotNull(message = "文章uid不能为空")
	private Long postId;
	/**
	 * 状态
	 */
	private Integer status;

}
