package cn.goroute.smart.post.mapper;

import cn.goroute.smart.post.entity.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Alickx
* @description 针对表【t_comment(文章回复表)】的数据库操作Mapper
* @createDate 2022-03-05 08:39:52
* @Entity cn.goroute.smart.post.entity.pojo.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




