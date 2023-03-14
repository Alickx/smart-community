package cn.goroute.smart.post.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 拓展信息
 * @TableName expand_info
 */
@TableName(value ="expand_info")
@Data
@Builder
@FieldNameConstants
public class ExpandInfoEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 目标id
     */
    private Long targetId;

	/**
	 * 类型
	 * see {@link cn.goroute.smart.post.constant.enums.PostItemTypeEnum}
	 */
	private Integer type;

    /**
     * 点赞量
     */
    private Integer thumbCount;

    /**
     * 评论量
     */
    private Integer commentCount;

    /**
     * 查看量
     */
    private Integer viewCount;

    /**
     * 收藏量
     */
    private Integer collectCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}