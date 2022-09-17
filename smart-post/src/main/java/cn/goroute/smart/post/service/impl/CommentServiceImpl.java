package cn.goroute.smart.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goroute.smart.post.domain.Comment;
import cn.goroute.smart.post.service.CommentService;
import cn.goroute.smart.post.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author caiguopeng
* @description 针对表【comment(文章回复表)】的数据库操作Service实现
* @createDate 2022-09-17 19:31:22
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




