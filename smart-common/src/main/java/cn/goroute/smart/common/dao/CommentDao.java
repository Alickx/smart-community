package cn.goroute.smart.common.dao;

import cn.goroute.smart.common.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Alickx
* @description 针对表【t_comment(文章回复表)】的数据库操作Mapper
* @createDate 2022-03-05 08:39:52
* @Entity cn.goroute.smart.common.entity.Comment
*/
@Mapper
public interface CommentDao extends BaseMapper<Comment> {

}




