package cn.goroute.smart.comment.dao;

import cn.goroute.smart.comment.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章回复表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:34:33
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
	
}
