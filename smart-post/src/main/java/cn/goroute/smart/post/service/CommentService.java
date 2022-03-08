package cn.goroute.smart.post.service;

import cn.goroute.smart.common.entity.Comment;
import cn.goroute.smart.common.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
* @author Alickx
* @description 针对表【t_comment(文章回复表)】的数据库操作Service
* @createDate 2022-03-05 08:39:52
*/
public interface CommentService extends IService<Comment> {

    R getCommentByPost(String postUid) throws IOException;

}
