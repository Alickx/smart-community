package cn.goroute.smart.post.service;

import cn.goroute.smart.post.entity.pojo.Comment;
import cn.goroute.smart.post.entity.vo.CommentVo;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.entity.resp.Response;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
* @author Alickx
* @description 针对表【t_comment(文章回复表)】的数据库操作Service
* @createDate 2022-03-05 08:39:52
*/
public interface CommentService extends IService<Comment> {

    Response getCommentByPost(QueryParam queryParam, Long postUid) throws IOException;

    Response del(CommentVo commentVo);

    Response saveComment(CommentVo commentVo);
}
