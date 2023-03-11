package cn.goroute.smart.user.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 用户收藏表
* @TableName user_collect
*/
@Data
public class UserCollectEntity implements Serializable {

    /**
    * 主键id
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 文章id
    */
    private Long postId;
	/**
	 * 逻辑删除
	 */
	private Boolean deleted;
    /**
    * 
    */
    private LocalDateTime createTime;
    /**
    * 
    */
    private LocalDateTime updateTime;
}
