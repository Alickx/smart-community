package cn.goroute.smart.post.service.impl;

import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.mapper.CommentMapper;
import cn.goroute.smart.post.service.CommentService;
import com.hccake.extend.mybatis.plus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【comment(文章回复表)】的数据库操作Service实现
* @createDate 2022-09-25 16:53:24
*/
@Service
public class CommentServiceImpl extends ExtendServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




