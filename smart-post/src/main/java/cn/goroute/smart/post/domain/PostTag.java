package cn.goroute.smart.post.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 文章标签表
 * @TableName post_tag
 */
@TableName(value ="post_tag")
@Data
public class PostTag implements Serializable {
    /**
     * 
     */
	@TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long postId;

    /**
     * 
     */
    private Long tagId;

    /**
     * 
     */
    private LocalDateTime createTime;

    /**
     * 
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}